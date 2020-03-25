package vn.ehealth.hl7.fhir.clinical.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Procedure;
import org.hl7.fhir.r4.model.Procedure.ProcedurePerformerComponent;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.Include;
import ca.uhn.fhir.rest.param.DateRangeParam;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.param.UriParam;
import vn.ehealth.hl7.fhir.clinical.entity.ProcedureEntity;
import vn.ehealth.hl7.fhir.core.entity.BaseResource;
import vn.ehealth.hl7.fhir.core.util.ConstantKeys;
import vn.ehealth.hl7.fhir.dao.BaseDao;
import vn.ehealth.hl7.fhir.dao.util.DatabaseUtil;

@Repository
public class ProcedureDao extends BaseDao<ProcedureEntity, Procedure> {

    @SuppressWarnings("deprecation")
    public List<IBaseResource> search(TokenParam active, ReferenceParam basedOn,
            TokenParam category, TokenParam code, ReferenceParam context, DateRangeParam date,
            ReferenceParam definition, ReferenceParam encounter, TokenParam identifier, ReferenceParam location,
            ReferenceParam partOf, ReferenceParam patient, ReferenceParam performer, TokenParam status,
            ReferenceParam subject, TokenParam resid, DateRangeParam _lastUpdated, TokenParam _tag, UriParam _profile,
            TokenParam _query, TokenParam _security, StringParam _content, StringParam _page, String sortParam,
            Integer count, Set<Include> includes) {
    	List<IBaseResource> resources = new ArrayList<IBaseResource>();
        Criteria criteria = setParamToCriteria(active, basedOn, category, code, context, date, definition, encounter,
                identifier, location, partOf, patient, performer, status, subject, resid, _lastUpdated, _tag, _profile,
                _query, _security, _content);
        Query query = new Query();
        if (criteria != null) {
            query = Query.query(criteria);
        }
        Pageable pageableRequest;
        pageableRequest = new PageRequest(_page != null ? Integer.valueOf(_page.getValue()) : ConstantKeys.PAGE,
                count != null ? count : ConstantKeys.DEFAULT_PAGE_SIZE);
        query.with(pageableRequest);
        if (sortParam != null && !sortParam.equals("")) {
            query.with(new Sort(Sort.Direction.DESC, sortParam));
        }else {
        	query.with(new Sort(Sort.Direction.DESC, "resUpdated"));
        	query.with(new Sort(Sort.Direction.DESC, "resCreated"));
		}
        List<ProcedureEntity> procedureEntitys = mongo.find(query, ProcedureEntity.class);
        if (procedureEntitys != null) {
            for (ProcedureEntity item : procedureEntitys) {
                Procedure obj = transform(item);
                // add more Resource as it's references
				if (includes != null && includes.size() > 0 && includes.contains(new Include("*"))) {
					if (obj.getSubject() != null) {
						Resource nested = DatabaseUtil.getResourceFromReference(obj.getSubject());
						if (nested != null) {
							obj.getSubject().setResource(nested);
//							if (!FPUtil.anyMatch(resources, x -> nested.getId().equals(x.getIdElement().getValue())))
//								resources.add(nested);
						}
					}
					if (obj.getEncounter() != null) {
						Resource nested = DatabaseUtil.getResourceFromReference(obj.getEncounter());
						if (nested != null) {
							obj.getEncounter().setResource(nested);
//							if (!FPUtil.anyMatch(resources, x -> nested.getId().equals(x.getIdElement().getValue())))
//								resources.add(nested);
						}
					}
					if (obj.getBasedOn() != null && obj.getBasedOn().size() > 0) {
						for (Reference ref : obj.getBasedOn()) {
							Resource nested = DatabaseUtil.getResourceFromReference(ref);
							if (nested != null) {
								ref.setResource(nested);
//								if (!FPUtil.anyMatch(resources, x -> nested.getId().equals(x.getIdElement().getValue())))
//									resources.add(nested);
							}
						}
					}
					if (obj.getAsserter() != null) {
						Resource nested = DatabaseUtil.getResourceFromReference(obj.getAsserter());
						if (nested != null) {
							obj.getAsserter().setResource(nested);
//							if (!FPUtil.anyMatch(resources, x -> nested.getId().equals(x.getIdElement().getValue())))
//								resources.add(nested);
						}
					}
					if (obj.getRecorder() != null) {
						Resource nested = DatabaseUtil.getResourceFromReference(obj.getRecorder());
						if (nested != null) {
							obj.getRecorder().setResource(nested);
//							if (!FPUtil.anyMatch(resources, x -> nested.getId().equals(x.getIdElement().getValue())))
//								resources.add(nested);
						}
					}
					if (obj.getReport() != null && obj.getReport().size() > 0) {
						for (Reference ref : obj.getReport()) {
							Resource nested = DatabaseUtil.getResourceFromReference(ref);
							if (nested != null) {
								ref.setResource(nested);
//								if (!FPUtil.anyMatch(resources, x -> nested.getId().equals(x.getIdElement().getValue())))
//									resources.add(nested);
							}
						}
					}
					if (obj.getLocation() != null) {
						Resource nested = DatabaseUtil.getResourceFromReference(obj.getLocation());
						if (nested != null) {
							obj.getLocation().setResource(nested);
//							if (!FPUtil.anyMatch(resources, x -> nested.getId().equals(x.getIdElement().getValue())))
//								resources.add(nested);
						}
					}
					if (obj.getReasonReference() != null && obj.getReasonReference().size() > 0) {
						for (Reference ref : obj.getReasonReference()) {
							Resource nested = DatabaseUtil.getResourceFromReference(ref);
							if (nested != null) {
								ref.setResource(nested);
//								if (!FPUtil.anyMatch(resources, x -> nested.getId().equals(x.getIdElement().getValue())))
//									resources.add(nested);
							}
						}
					}
					if (obj.getPerformer() != null && obj.getPerformer().size() > 0) {
						for (ProcedurePerformerComponent ref : obj.getPerformer()) {
							Resource nested = DatabaseUtil.getResourceFromReference(ref.getActor());
							if (nested != null) {
								ref.getActor().setResource(nested);
//								if (!FPUtil.anyMatch(resources, x -> nested.getId().equals(x.getIdElement().getValue())))
//									resources.add(nested);
							}
							Resource nested1 = DatabaseUtil.getResourceFromReference(ref.getOnBehalfOf());
							if (nested1 != null) {
								ref.getOnBehalfOf().setResource(nested);
//								if (!FPUtil.anyMatch(resources, x -> nested.getId().equals(x.getIdElement().getValue())))
//									resources.add(nested);
							}
						}
					}
					if (obj.getReasonReference() != null && obj.getReasonReference().size() > 0) {
						for (Reference ref : obj.getReasonReference()) {
							Resource nested = DatabaseUtil.getResourceFromReference(ref);
							if (nested != null) {
								ref.setResource(nested);
//								if (!FPUtil.anyMatch(resources, x -> nested.getId().equals(x.getIdElement().getValue())))
//									resources.add(nested);
							}
						}
					}
					if (obj.getComplicationDetail() != null && obj.getComplicationDetail().size() > 0) {
						for (Reference ref : obj.getComplicationDetail()) {
							Resource nested = DatabaseUtil.getResourceFromReference(ref);
							if (nested != null) {
								ref.setResource(nested);
//								if (!FPUtil.anyMatch(resources, x -> nested.getId().equals(x.getIdElement().getValue())))
//									resources.add(nested);
							}
						}
					}
					if (obj.getUsedReference() != null && obj.getUsedReference().size() > 0) {
						for (Reference ref : obj.getUsedReference()) {
							Resource nested = DatabaseUtil.getResourceFromReference(ref);
							if (nested != null) {
								ref.setResource(nested);
//								if (!FPUtil.anyMatch(resources, x -> nested.getId().equals(x.getIdElement().getValue())))
//									resources.add(nested);
							}
						}
					}
				} else {
					if (includes != null && includes.size() > 0 && includes.contains(new Include("Procedure:subject"))
							&& obj.getSubject() != null) {
						Resource nested = DatabaseUtil.getResourceFromReference(obj.getSubject());
						if (nested != null) {
							obj.getSubject().setResource(nested);
//							if (!FPUtil.anyMatch(resources, x -> nested.getId().equals(x.getIdElement().getValue())))
//								resources.add(nested);
						}
					}
					if (includes != null && includes.size() > 0
							&& includes.contains(new Include("Procedure:encounter")) && obj.getEncounter() != null) {
						Resource nested = DatabaseUtil.getResourceFromReference(obj.getEncounter());
						if (nested != null) {
							obj.getEncounter().setResource(nested);
//							if (!FPUtil.anyMatch(resources, x -> nested.getId().equals(x.getIdElement().getValue())))
//								resources.add(nested);
						}
					}
				}
                resources.add(obj);
            }
        }
        return resources;
    }

    public long countMatchesAdvancedTotal(FhirContext fhirContext, TokenParam active, ReferenceParam basedOn,
            TokenParam category, TokenParam code, ReferenceParam context, DateRangeParam date,
            ReferenceParam definition, ReferenceParam encounter, TokenParam identifier, ReferenceParam location,
            ReferenceParam partOf, ReferenceParam patient, ReferenceParam performer, TokenParam status,
            ReferenceParam subject, TokenParam resid, DateRangeParam _lastUpdated, TokenParam _tag, UriParam _profile,
            TokenParam _query, TokenParam _security, StringParam _content) {
        long total = 0;
        Criteria criteria = setParamToCriteria(active, basedOn, category, code, context, date, definition, encounter,
                identifier, location, partOf, patient, performer, status, subject, resid, _lastUpdated, _tag, _profile,
                _query, _security, _content);
        Query query = new Query();
        if (criteria != null) {
            query = Query.query(criteria);
        }
        total = mongo.count(query, ProcedureEntity.class);
        return total;
    }


    private Criteria setParamToCriteria(TokenParam active, ReferenceParam basedOn, TokenParam category,
            TokenParam code, ReferenceParam context, DateRangeParam date, ReferenceParam definition,
            ReferenceParam encounter, TokenParam identifier, ReferenceParam location, ReferenceParam partOf,
            ReferenceParam patient, ReferenceParam performer, TokenParam status, ReferenceParam subject,
            TokenParam resid, DateRangeParam _lastUpdated, TokenParam _tag, UriParam _profile, TokenParam _query,
            TokenParam _security, StringParam _content) {
        Criteria criteria = null;
        // active
        if (active != null) {
            criteria = Criteria.where("active").is(active);
        } else {
            criteria = Criteria.where("active").is(true);
        }
        // set param default
        criteria = DatabaseUtil.addParamDefault2Criteria(criteria, resid, _lastUpdated, _tag, _profile, _security,
                identifier);
        // based-on
        if (basedOn != null) {
            if(basedOn.getValue().indexOf("|")==-1) {
                criteria.orOperator(Criteria.where("basedOn.reference").is(basedOn.getValue()),
                        Criteria.where("basedOn.display").is(basedOn.getValue()));
            }else {
                String[] ref= basedOn.getValue().split("\\|");
                criteria.and("basedOn.identifier.system").is(ref[0]).and("basedOn.identifier.value").is(ref[1]);
            }
        }
        // category
        if (category != null) {
            criteria.and("category.coding.code.myStringValue").is(category.getValue());
        }
        // code
        if (code != null) {
            criteria.and("code.coding.code.myStringValue").is(code.getValue());
        }
        // context
        if (context != null) {
            if(context.getValue().indexOf("|")==-1) {
                criteria.orOperator(Criteria.where("context.reference").is(context.getValue()),
                        Criteria.where("context.display").is(context.getValue()));
            }else {
                String[] ref= context.getValue().split("\\|");
                criteria.and("context.identifier.system").is(ref[0]).and("context.identifier.value").is(ref[1]);
            }
        }
        // date
        if (date != null) {
            criteria = DatabaseUtil.setTypeDateToCriteria(criteria, "performed", date);
        }
        // definition
        if (definition != null) {
            if(definition.getValue().indexOf("|")==-1) {
                criteria.orOperator(Criteria.where("definition.reference").is(definition.getValue()),
                        Criteria.where("definition.display").is(definition.getValue()));
            }else {
                String[] ref= definition.getValue().split("\\|");
                criteria.and("definition.identifier.system").is(ref[0]).and("definition.identifier.value").is(ref[1]);
            }
        }
        // encounter
        if (encounter != null) {
            if(encounter.getValue().indexOf("|")==-1) {
                criteria.orOperator(Criteria.where("context.reference").is(encounter.getValue()),
                        Criteria.where("context.display").is(encounter.getValue()));
            }else {
                String[] ref= encounter.getValue().split("\\|");
                criteria.and("context.identifier.system").is(ref[0]).and("context.identifier.value").is(ref[1]);
            }
        }
        // location
        if (location != null) {
            if(location.getValue().indexOf("|")==-1) {
                criteria.orOperator(Criteria.where("location.location.reference").is(location.getValue()),
                        Criteria.where("location.location.display").is(location.getValue()));
            }else {
                String[] ref= location.getValue().split("\\|");
                criteria.and("location.location.identifier.system").is(ref[0]).and("location.location.identifier.value").is(ref[1]);
            }
        }
        // part-of
        if (partOf != null) {
            if(partOf.getValue().indexOf("|")==-1) {
                criteria.orOperator(Criteria.where("partOf.reference").is(partOf.getValue()),
                        Criteria.where("partOf.display").is(partOf.getValue()));
            }else {
                String[] ref= partOf.getValue().split("\\|");
                criteria.and("partOf.identifier.system").is(ref[0]).and("partOf.identifier.value").is(ref[1]);
            }
        }
        // patient
        if (patient != null) {
            if(patient.getValue().indexOf("|")==-1) {
                criteria.orOperator(Criteria.where("subject.reference").is(patient.getValue()),
                        Criteria.where("subject.display").is(patient.getValue()));
            }else {
                String[] ref= patient.getValue().split("\\|");
                criteria.and("subject.identifier.system").is(ref[0]).and("subject.identifier.value").is(ref[1]);
            }
        }
        // performer
        if (performer != null) {
            if(performer.getValue().indexOf("|")==-1) {
                criteria.orOperator(Criteria.where("performer.reference").is(performer.getValue()),
                        Criteria.where("performer.display").is(performer.getValue()));
            }else {
                String[] ref= performer.getValue().split("\\|");
                criteria.and("performer.identifier.system").is(ref[0]).and("performer.identifier.value").is(ref[1]);
            }
        }
        // status
        if (status != null) {
            criteria.and("status").is(status.getValue());
        }
        // subject
        if (subject != null) {
            if(subject.getValue().indexOf("|")==-1) {
                criteria.orOperator(Criteria.where("subject.reference").is(subject.getValue()),
                        Criteria.where("subject.display").is(subject.getValue()));
            }else {
                String[] ref= subject.getValue().split("\\|");
                criteria.and("subject.identifier.system").is(ref[0]).and("subject.identifier.value").is(ref[1]);
            }
        }
        return criteria;
    }
                    
    protected String getProfile() {
        return "Procedure-v1.0";
    }

    @Override
    protected ProcedureEntity fromFhir(Procedure obj) {
        return ProcedureEntity.fromProcedure(obj);
    }

    @Override
    protected Procedure toFhir(ProcedureEntity ent) {
        return ProcedureEntity.toProcedure(ent);
    }

    @Override
    protected Class<? extends BaseResource> getEntityClass() {
        return ProcedureEntity.class;
    }

}
