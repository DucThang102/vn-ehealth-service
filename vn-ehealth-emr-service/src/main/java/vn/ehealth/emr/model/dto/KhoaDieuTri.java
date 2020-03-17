package vn.ehealth.emr.model.dto;

import vn.ehealth.emr.service.ServiceFactory;
import vn.ehealth.emr.utils.Constants.CodeSystemValue;
import vn.ehealth.hl7.fhir.core.util.StringUtil;
import static vn.ehealth.hl7.fhir.core.util.DataConvertUtil.*;
import static vn.ehealth.emr.utils.FhirUtil.*;

import org.hl7.fhir.r4.model.Location;
import org.hl7.fhir.r4.model.Reference;

public class KhoaDieuTri extends BaseModelDTO {

    public String ma;
    public String ten;
    public DanhMuc dmLoaiKhoa;
    
    public KhoaDieuTri() {
        super();
    }
    
    public KhoaDieuTri(Location ent) {
        super(ent);
        if(ent == null) return;
        
        this.ma = ent.hasIdentifier()? ent.getIdentifierFirstRep().getValue() : "";
        this.ten = ent.hasName()? ent.getName() : "";
        this.dmLoaiKhoa = new DanhMuc(findConceptBySystem(ent.getType(), CodeSystemValue.KHOA_DIEU_TRI));
    }
    
    public static KhoaDieuTri fromFhir(Location ent) {
        if(ent == null) return null;
        return new KhoaDieuTri(ent);        
    }
    
    public static KhoaDieuTri fromReference(Reference ref) {
        if(ref != null && ref.hasReference()) {
            var ent = ServiceFactory.getLocationService().getById(ref.getReference());
            return fromFhir(ent);
        }
        return null;        
    }
    
    public static Location toFhir(KhoaDieuTri dto) {
        if(dto == null) return null;
        var ent = ServiceFactory.getLocationService().getById(dto.id);
        
        if(ent == null) {
            ent = new Location();
            ent.setId(StringUtil.generateUID());
        }
        
        ent.setIdentifier(listOf(createIdentifier(dto.ma, CodeSystemValue.CO_SO_KHAM_BENH)));
        ent.setName(dto.ten);
        ent.setType(listOf(DanhMuc.toConcept(dto.dmLoaiKhoa, CodeSystemValue.KHOA_DIEU_TRI)));
        return ent;
    }
}