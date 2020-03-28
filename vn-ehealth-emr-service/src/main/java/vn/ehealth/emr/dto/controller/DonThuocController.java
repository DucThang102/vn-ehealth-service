package vn.ehealth.emr.dto.controller;

import static vn.ehealth.hl7.fhir.core.util.DataConvertUtil.mapOf;
import static vn.ehealth.hl7.fhir.core.util.DataConvertUtil.transform;
import static vn.ehealth.hl7.fhir.core.util.FhirUtil.createIdType;
import static vn.ehealth.hl7.fhir.core.util.FhirUtil.createIdentifier;
import static vn.ehealth.hl7.fhir.dao.util.DatabaseUtil.setReferenceResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hl7.fhir.r4.model.Encounter;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.MedicationRequest;
import org.hl7.fhir.r4.model.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.uhn.fhir.rest.param.TokenParam;
import vn.ehealth.emr.model.dto.BaseRef;
import vn.ehealth.emr.model.dto.DonThuocChiTiet;
import vn.ehealth.emr.model.dto.VaoKhoa;
import vn.ehealth.emr.model.dto.DonThuocChiTiet.DonThuoc;
import vn.ehealth.emr.utils.Constants.CodeSystemValue;
import vn.ehealth.emr.utils.Constants.IdentifierSystem;
import vn.ehealth.hl7.fhir.dao.util.DaoFactory;
import vn.ehealth.hl7.fhir.medication.dao.impl.MedicationRequestDao;

@RestController
@RequestMapping("/api/donthuoc")
public class DonThuocController {
	
	private static Logger logger = LoggerFactory.getLogger(DonThuocController.class);
	@Autowired private MedicationRequestDao medicationRequestDao;
	
	private static MedicationRequest saveMedicationRequest(MedicationRequest obj) {
        if(obj != null) {
            var medicationRequestDao = DaoFactory.getMedicationRequestDao();
            if(obj.hasId()) {
                return medicationRequestDao.update(obj, obj.getIdElement());
            }else {
                return medicationRequestDao.create(obj);
            }
        }
        return null;
    }
	 
	@GetMapping("/get_by_id")
    public ResponseEntity<?> getById(@RequestParam String id) {
        var obj = medicationRequestDao.read(new IdType(id));
        var dto = DonThuocChiTiet.fromFhir(obj);
        return ResponseEntity.ok(dto);
    }
    
	@GetMapping("/get_donthuoc_list")
    public ResponseEntity<?> getDonThuocList(@RequestParam String soDon) {
		var params = mapOf("groupIdentifier", createIdentifier(soDon, IdentifierSystem.DON_THUOC));
		var lists = medicationRequestDao.search(params);
		var result = transform(lists, x -> DonThuocChiTiet.fromFhir((MedicationRequest) x));
		return ResponseEntity.ok(result);
    }
    
    @SuppressWarnings("unchecked")
	@PostMapping("/save_don_thuoc")
    public ResponseEntity<?> save(@RequestBody DonThuoc dto) {
        try {
	    	if(dto == null || dto.encounter == null) return null;
	        var enc = DaoFactory.getEncounterDao().read(createIdType(dto.encounter.id));
	        if(enc == null) return null;
             
            dto.patient = new BaseRef(enc.getSubject());
            var entities = DonThuocChiTiet.toFhir(dto); 
            var medicationRequests = (List<MedicationRequest>) entities.get("medicationRequests");           
            var listDonThuocChiTiet = new ArrayList<DonThuocChiTiet>();      
            var medicationRequestDao = DaoFactory.getMedicationRequestDao();
            
            if(medicationRequests != null) {   
                for(var medicationRequest : medicationRequests) {
                	
                	var params = mapOf("groupIdentifier", createIdentifier(dto.soDon, IdentifierSystem.DON_THUOC));
                	var oldOmedicationRequests = medicationRequestDao.search(params);
                    oldOmedicationRequests.forEach(x -> medicationRequestDao.remove(x.getIdElement()));
                    medicationRequest = saveMedicationRequest(medicationRequest);
                	var donThuocChiTiet = DonThuocChiTiet.fromFhir(medicationRequest);
                	listDonThuocChiTiet.add(donThuocChiTiet);
                	
                }
            }
            
            var result = mapOf("success", true, "donthuoc", listDonThuocChiTiet);
            return ResponseEntity.ok(result);
        }catch(Exception e) {
            logger.error("Can not save entity: ", e);
            var error = Optional.ofNullable(e.getMessage()).orElse("Unknown error");
            var result = mapOf("success", false, "error", error);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
}
