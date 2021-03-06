package vn.ehealth.hl7.fhir.diagnostic.entity;

import java.util.List;

import org.hl7.fhir.r4.model.Type;

import vn.ehealth.hl7.fhir.core.entity.BaseCodeableConcept;

import org.hl7.fhir.r4.model.Observation.ObservationComponentComponent;

import com.fasterxml.jackson.annotation.JsonIgnore;

import static vn.ehealth.hl7.fhir.core.util.DataConvertUtil.transform;

public class ObservationComponentEntity {
    public BaseCodeableConcept code;
    @JsonIgnore public Type value;
    public BaseCodeableConcept dataAbsentReason;
    public List<BaseCodeableConcept> interpretation;
    public List<ObservationReferenceRangeEntity> referenceRange;
    
    public static ObservationComponentEntity fromObservationComponentComponent(ObservationComponentComponent obj) {
        if(obj == null) return null;
        
        var ent = new ObservationComponentEntity();
        ent.code = obj.hasCode()? BaseCodeableConcept.fromCodeableConcept(obj.getCode()) : null;
        ent.value = obj.hasValue()? obj.getValue() : null;
        ent.dataAbsentReason = obj.hasDataAbsentReason()? BaseCodeableConcept.fromCodeableConcept(obj.getDataAbsentReason()) : null;
        ent.interpretation = obj.hasInterpretation()? BaseCodeableConcept.fromCodeableConcept(obj.getInterpretation()) : null;
        ent.referenceRange = obj.hasReferenceRange()? transform(obj.getReferenceRange(), 
                                    ObservationReferenceRangeEntity::fromObservationReferenceRangeComponent) : null;
        return ent;
    }
    
    public static ObservationComponentComponent toObservationComponentComponent(ObservationComponentEntity ent) {
        if(ent == null) return null;
        
        var obj = new ObservationComponentComponent();
        obj.setCode(BaseCodeableConcept.toCodeableConcept(ent.code));
        obj.setValue(ent.value);
        obj.setDataAbsentReason(BaseCodeableConcept.toCodeableConcept(ent.dataAbsentReason));
        obj.setInterpretation(BaseCodeableConcept.toCodeableConcept(ent.interpretation));
        obj.setReferenceRange(transform(ent.referenceRange,  ObservationReferenceRangeEntity::toObservationReferenceRangeComponent));
        
        return obj;
    }
}
