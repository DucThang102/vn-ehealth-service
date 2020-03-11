package vn.ehealth.hl7.fhir.clinical.entity;

import java.util.List;



import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Condition.ConditionEvidenceComponent;

import vn.ehealth.hl7.fhir.core.entity.BaseReference;

public class ConditionEvidenceEntity {

    public List<CodeableConcept> code;
    public List<BaseReference> detail;
    
    public static ConditionEvidenceEntity fromConditionEvidenceComponent(ConditionEvidenceComponent obj) {
        if(obj == null) return null;
        
        var ent = new ConditionEvidenceEntity();
        
        ent.code = obj.getCode();
        ent.detail = BaseReference.fromReferenceList(obj.getDetail());
        
        return ent;
    }
    
    public static ConditionEvidenceComponent toConditionEvidenceComponent(ConditionEvidenceEntity ent) {
        if(ent == null) return null;
        
        var obj = new ConditionEvidenceComponent();
        
        obj.setCode(ent.code);
        obj.setDetail(BaseReference.toReferenceList(ent.detail));
        
        return obj;
    }
}
