package vn.ehealth.hl7.fhir.diagnostic.entity;

import java.util.List;



import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Type;
import org.hl7.fhir.r4.model.Specimen.SpecimenProcessingComponent;

import vn.ehealth.hl7.fhir.core.entity.BaseReference;

public class SpecimenProcessingEntity {

    public String description;
    public CodeableConcept procedure;
    public List<BaseReference> additive;
    public Type time;
    
    public static SpecimenProcessingEntity fromSpecimenProcessingComponent(SpecimenProcessingComponent obj) {
        if(obj == null) return null;
        
        var ent = new SpecimenProcessingEntity();
        
        ent.description = obj.getDescription();
        ent.procedure = obj.getProcedure();
        ent.additive = BaseReference.fromReferenceList(obj.getAdditive());
        ent.time = obj.getTime();
        
        return ent;
    }
    
    public static SpecimenProcessingComponent toSpecimenProcessingComponent(SpecimenProcessingEntity ent) {
        if(ent == null) return null;
        
        var obj = new SpecimenProcessingComponent();
        obj.setDescription(ent.description);
        obj.setProcedure(ent.procedure);
        obj.setAdditive(BaseReference.toReferenceList(ent.additive));
        obj.setTime(ent.time);
        
        return obj;
    }
}
