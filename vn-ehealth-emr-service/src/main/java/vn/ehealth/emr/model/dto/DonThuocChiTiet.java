package vn.ehealth.emr.model.dto;

import static vn.ehealth.hl7.fhir.core.util.DataConvertUtil.mapOf;
import static vn.ehealth.hl7.fhir.core.util.FhirUtil.createIdType;
import static vn.ehealth.hl7.fhir.core.util.FhirUtil.createIdentifier;
import static vn.ehealth.hl7.fhir.core.util.FhirUtil.createReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Encounter;
import org.hl7.fhir.r4.model.MedicationRequest;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.ResourceType;
import org.hl7.fhir.r4.model.Timing.UnitsOfTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import vn.ehealth.emr.utils.Constants.CodeSystemValue;
import vn.ehealth.emr.utils.Constants.IdentifierSystem;
import vn.ehealth.hl7.fhir.dao.util.DaoFactory;

public class DonThuocChiTiet extends BaseModelDTO {
	
	public static class TanXuatDungThuoc {
        public Integer soLan;
        public String donViThoiGian;
    }
	
	public static class LieuLuong {
        public long soLuong;
        public String donVi;
    }

	public static class DonThuoc {
		public BaseRef encounter;
		public BaseRef patient;
		public BaseRef bacSiKeDon;   
	    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	    public Date ngayKeDon; 
	    public String soDon;
	    public List<DonThuocChiTiet> dsDonThuocChiTiet;
        
    }
	
	public BaseRef encounter;
	public BaseRef patient;
	public BaseRef bacSiKeDon;   
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public Date ngayKeDon; 
    public String soDon;
    public DanhMuc dmThuoc;
    public DanhMuc dmDuongDungThuoc;
    public TanXuatDungThuoc tanXuatDungThuoc;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public Date ngayBatDau;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public Date ngayKetThuc;
    public LieuLuong lieuLuong;
    public String chiDan;
 
    
    public DonThuocChiTiet() {
        super();
    }
    
    
    public DonThuocChiTiet(MedicationRequest obj) {
        super(obj);
        
        if(obj == null) return;
        
        this.patient = BaseRef.fromPatientRef(obj.getSubject());
        
        this.encounter = BaseRef.fromVaoKhoaEncounterRef(obj.getEncounter());
        this.bacSiKeDon = BaseRef.fromPractitionerRef(obj.getRequester());
        this.ngayKeDon = obj.getAuthoredOn();
        this.soDon = obj.hasGroupIdentifier()? obj.getGroupIdentifier().getValue(): "";
        
        this.dmThuoc =  DanhMuc.fromConcept(obj.getMedicationCodeableConcept());
        this.dmDuongDungThuoc = obj.hasDosageInstruction() ? DanhMuc.fromConcept(obj.getDosageInstructionFirstRep().getRoute()) : null;
        if (this.tanXuatDungThuoc == null) {
        	this.tanXuatDungThuoc = new TanXuatDungThuoc();
        }
        
        this.tanXuatDungThuoc.soLan = obj.hasDosageInstruction() ? obj.getDosageInstructionFirstRep().getTiming().getRepeat().getCount() : null;
        this.tanXuatDungThuoc.donViThoiGian = obj.hasDosageInstruction() ? obj.getDosageInstructionFirstRep().getTiming().getRepeat().getDurationUnit().toCode() : null;
        
        if (this.lieuLuong == null) {
        	this.lieuLuong = new LieuLuong();
        }
        
        this.lieuLuong.donVi = obj.hasDispenseRequest() ? obj.getDispenseRequest().getQuantity().getUnit() : null;
        this.lieuLuong.soLuong = obj.hasDispenseRequest() ? obj.getDispenseRequest().getQuantity().getValue().longValue() : null;
        		
		this.ngayBatDau = obj.hasDispenseRequest() ? obj.getDispenseRequest().getValidityPeriod().getStart() : null;
		this.ngayKetThuc = obj.hasDispenseRequest() ? obj.getDispenseRequest().getValidityPeriod().getEnd() : null;
		this.chiDan = obj.hasDosageInstruction() ? obj.getDosageInstructionFirstRep().getText() : null;

    }
    
    public static DonThuocChiTiet fromFhir(MedicationRequest obj) {
        if(obj == null) return null;
        return new DonThuocChiTiet(obj);
    }
    
    public static Map<String, Object> toFhir(DonThuoc dto) {
        if(dto == null) return null;
//        var subject = dto.patient != null? createReference(ResourceType.Patient, dto.patient.id) : null;
//    	var encounter = dto.encounter != null? createReference(ResourceType.Encounter, dto.encounter.id) : null;
        
        if(dto == null || dto.encounter == null) return null;
        var enc = DaoFactory.getEncounterDao().read(createIdType(dto.encounter.id));
        if(enc == null) return null;
        
        dto.patient = new BaseRef(enc.getSubject());
    	
    	var subject = BaseRef.toPatientRef(dto.patient);
    	var encounter = BaseRef.toEncounterRef(dto.encounter);
        var medicationRequest = new MedicationRequest();
        medicationRequest.setSubject(subject);
        medicationRequest.setEncounter(encounter);
        if(dto.bacSiKeDon != null) {
        	medicationRequest.setRequester(BaseRef.toPractitionerRef(dto.bacSiKeDon));
        }
      
        medicationRequest.setAuthoredOn(dto.ngayKeDon);
        medicationRequest.setGroupIdentifier(createIdentifier(dto.soDon, IdentifierSystem.DON_THUOC));
        
        var medicationRequests = new ArrayList<MedicationRequest>();
        if(dto.dsDonThuocChiTiet != null) {
        	
            for(var donThuocChiTiet : dto.dsDonThuocChiTiet) {
                var mRequest = new MedicationRequest();
                mRequest.setSubject(medicationRequest.getSubject());
                mRequest.setEncounter(medicationRequest.getEncounter());
                if(dto.bacSiKeDon != null) {
                	mRequest.setRequester(medicationRequest.getRequester());
                }
                mRequest.setAuthoredOn(medicationRequest.getAuthoredOn());
                mRequest.setGroupIdentifier(medicationRequest.getGroupIdentifier());
                mRequest.setMedication(DanhMuc.toConcept(donThuocChiTiet.dmThuoc, CodeSystemValue.DM_THUOC));
               
                Dosage d = new Dosage();
                d.setText(donThuocChiTiet.chiDan);
                d.setRoute(DanhMuc.toConcept(donThuocChiTiet.dmDuongDungThuoc, CodeSystemValue.DM_DUONG_DUNG_THUOC));
                d.getTiming().getRepeat().setCount(donThuocChiTiet.tanXuatDungThuoc.soLan);
                d.getTiming().getRepeat().setDurationUnit(UnitsOfTime.fromCode(donThuocChiTiet.tanXuatDungThuoc.donViThoiGian));
                mRequest.getDosageInstruction().add(d);
                
                mRequest.getDispenseRequest().getQuantity().setValue(donThuocChiTiet.lieuLuong.soLuong);
                mRequest.getDispenseRequest().getQuantity().setUnit(donThuocChiTiet.lieuLuong.donVi);
                mRequest.getDispenseRequest().getValidityPeriod().setStart(donThuocChiTiet.ngayBatDau);
                mRequest.getDispenseRequest().getValidityPeriod().setEnd(donThuocChiTiet.ngayKetThuc);  
                medicationRequests.add(mRequest);
            }
        }    
        return mapOf(
                "medicationRequest", medicationRequest,
                "medicationRequests", medicationRequests
             );
    }


	@Override
	public ResourceType getType() {
		// TODO Auto-generated method stub
		return null;
	}
    

}
