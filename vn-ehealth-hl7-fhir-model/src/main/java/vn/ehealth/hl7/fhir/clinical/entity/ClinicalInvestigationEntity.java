package vn.ehealth.hl7.fhir.clinical.entity;

import java.util.List;

import org.hl7.fhir.r4.model.ClinicalImpression.ClinicalImpressionInvestigationComponent;

import vn.ehealth.hl7.fhir.core.entity.BaseCodeableConcept;
import vn.ehealth.hl7.fhir.core.entity.BaseReference;

public class ClinicalInvestigationEntity {

    public BaseCodeableConcept code;
    public List<BaseReference> item;
    
    public static ClinicalInvestigationEntity fromClinicalImpressionInvestigationComponent(ClinicalImpressionInvestigationComponent obj) {
        if(obj == null) return null;
        
        var ent = new ClinicalInvestigationEntity();
        
        ent.code = BaseCodeableConcept.fromCodeableConcept(obj.getCode());
        ent.item = BaseReference.fromReferenceList(obj.getItem());
        
        return ent;
    }
    
    public static ClinicalImpressionInvestigationComponent toClinicalImpressionInvestigationComponent(ClinicalInvestigationEntity ent) {
        if(ent == null) return null;
        
        var obj = new ClinicalImpressionInvestigationComponent();
        
        obj.setCode(BaseCodeableConcept.toCodeableConcept(ent.code));
        obj.setItem(BaseReference.toReferenceList(ent.item));
        return obj;
    }
}
