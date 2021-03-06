package vn.ehealth.hl7.fhir.term.entity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.hl7.fhir.r4.model.ConceptMap;
import org.hl7.fhir.r4.model.Enumerations.PublicationStatus;
import org.hl7.fhir.r4.model.Type;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import vn.ehealth.hl7.fhir.core.entity.BaseCodeableConcept;
import vn.ehealth.hl7.fhir.core.entity.BaseIdentifier;
import vn.ehealth.hl7.fhir.core.entity.BaseResource;
import vn.ehealth.hl7.fhir.core.entity.BaseUsageContext;

import static vn.ehealth.hl7.fhir.core.util.DataConvertUtil.transform;

/**
 * @author SONVT24
 * @since 2019
 * @version 1.0
 */
@Document(collection = "conceptMap")
@CompoundIndex(def = "{'fhirId':1,'active':1,'version':1}", name = "index_by_default")
public class ConceptMapEntity extends BaseResource {
    @Id
    public ObjectId id;
    public String url;
    public BaseIdentifier identifier;
    public String name;
    public String title;
    public String status;
    public boolean experimental;
    public Date date;
    public String publisher;
    public List<ContactDetailEntity> contact;
    public String description;
    public List<BaseUsageContext> useContext;
    public List<BaseCodeableConcept> jurisdiction;
    public String purpose;
    public String copyright;
    @JsonIgnore public Type source;
    @JsonIgnore public Type target;
    public List<GroupElementEntity> group;// ConceptMapGroupComponent
    
    public static ConceptMapEntity fromConceptMap(ConceptMap obj) {
        if(obj == null) return null;
        var ent = new ConceptMapEntity();
        ent.url = obj.getUrl();
        ent.identifier = BaseIdentifier.fromIdentifier(obj.getIdentifier());
        ent.name = obj.getName();
        ent.title = obj.getTitle();
        ent.status = Optional.ofNullable(obj.getStatus()).map(x -> x.toCode()).orElse(null);
        ent.experimental = obj.getExperimental();
        ent.date = obj.getDate();
        ent.publisher = obj.getPublisher();
        ent.contact = transform(obj.getContact(), ContactDetailEntity::fromContactDetail);
        ent.description = obj.getDescription();
        ent.useContext = transform(obj.getUseContext(), BaseUsageContext::fromUsageContext);
        ent.jurisdiction = BaseCodeableConcept.fromCodeableConcept(obj.getJurisdiction());
        ent.purpose = obj.getPurpose();
        ent.copyright = obj.getCopyright();
        ent.source = obj.getSource();
        ent.target = obj.getTarget();
        ent.group = transform(obj.getGroup(), GroupElementEntity::fromConceptMapGroupComponent);
        return ent;
    }
    
    public static ConceptMap toConceptMap(ConceptMapEntity ent) {
        if(ent == null) return null;
        var obj = new ConceptMap();
        obj.setUrl(ent.url);
        obj.setIdentifier(BaseIdentifier.toIdentifier(ent.identifier));
        obj.setName(ent.name);
        obj.setTitle(ent.title);
        obj.setStatus(PublicationStatus.fromCode(ent.status));
        obj.setExperimental(ent.experimental);
        obj.setDate(ent.date);
        obj.setPublisher(ent.publisher);
        obj.setContact(transform(ent.contact, ContactDetailEntity::toContactDetail));
        obj.setDescription(ent.description);
        obj.setUseContext(transform(ent.useContext, BaseUsageContext::toUsageContext));
        obj.setJurisdiction(BaseCodeableConcept.toCodeableConcept(ent.jurisdiction));
        obj.setPurpose(ent.purpose);
        obj.setCopyright(ent.copyright);
        obj.setSource(ent.source);
        obj.setTarget(ent.target);
        obj.setGroup(transform(ent.group, GroupElementEntity::toConceptMapGroupComponent));
        return obj;
    }
}
