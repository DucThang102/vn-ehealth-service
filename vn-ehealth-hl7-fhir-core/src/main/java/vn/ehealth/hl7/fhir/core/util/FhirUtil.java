package vn.ehealth.hl7.fhir.core.util;

import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;

import org.hl7.fhir.r4.model.Address;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.ContactPoint.ContactPointSystem;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Period;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.Resource;

public class FhirUtil {

    public static CodeableConcept findConceptBySystem(List<CodeableConcept> lst, @Nonnull String system) {
        if(lst == null) return null;
        
        for(var concept : lst) {
            if(FPUtil.anyMatch(concept.getCoding(), x -> system.equals(x.getSystem()))) {
                return concept;
            };
        }
        return null;
    }
    
    public static Identifier createIdentifier(String value, String system) {
        var identifier = new Identifier();
        identifier.setValue(value);
        identifier.setSystem(system);
        return identifier;
    }
    
    public static Period createPeriod(Date start, Date end) {
        var period = new Period();
        period.setStart(start);
        period.setEnd(end);
        return period;
    }
    
    public static Identifier createIdentifier(String value, String system, Date start, Date end) {
        var identifier = new Identifier();
        identifier.setValue(value);
        identifier.setSystem(value);
        identifier.setPeriod(createPeriod(start, end));
        return identifier;
    }
    
    public static Address createAddress(String text) {
        if(text == null) return null;
        var address = new Address();
        address.setText(text);
        return address;
    }
    
    public static HumanName createHumanName(String text) {
        if(text == null) return null;
        var name = new HumanName();
        name.setText(text);
        return name;
    }
    
    
    public static ContactPoint createContactPoint(String value, String system) {
        var contactPoint = new ContactPoint();
        contactPoint.setValue(value);
        contactPoint.setSystem(ContactPointSystem.fromCode(system));
        return contactPoint;
    }
    
    public static CodeableConcept createCodeableConcept(String text) {
        if(text == null) return null;
        var concept = new CodeableConcept();
        concept.setText(text);
        return concept;
    }
    
    public static CodeableConcept createCodeableConcept(String code, String name, String system) {
        var concept = new CodeableConcept();
        concept.setText(name);
        var coding = new Coding();
        coding.setCode(code);
        coding.setDisplay(name);
        coding.setSystem(system);
        concept.addCoding(coding);
        return concept;
    }
    
    public static Reference createReference(Resource resource) {
        if(resource == null) return null;
        return new Reference(resource.getId());
    }
    
    public static IdType createIdType(String id) {
        return new IdType(id);
    }
    
    public static IdType createIdType(Reference ref) {
        if((ref != null && ref.hasReference())) {
            return new IdType(ref.getReference());
        }
        return null;
    }
}