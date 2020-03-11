package vn.ehealth.hl7.fhir.clinical.entity;

import java.util.List;



import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Condition.ConditionStageComponent;

import vn.ehealth.hl7.fhir.core.entity.BaseReference;

public class ConditionStageEntity {
    
    public CodeableConcept summary;
    public List<BaseReference> assessment;
    
    public static ConditionStageEntity fromConditionStageComponent(ConditionStageComponent obj) {
        if(obj == null) return null;
        
        var ent = new ConditionStageEntity();
        
        ent.summary = obj.getSummary();
        ent.assessment = BaseReference.fromReferenceList(obj.getAssessment());
        
        return ent;
    }
    
    public static ConditionStageComponent toConditionStageComponent(ConditionStageEntity ent) {
        if(ent == null) return null;
        
        var obj = new ConditionStageComponent();
        
        obj.setSummary(ent.summary);
        obj.setAssessment(BaseReference.toReferenceList(ent.assessment));
        
        return obj;
    }
}
