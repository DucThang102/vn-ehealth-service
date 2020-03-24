package vn.ehealth.hl7.fhir.clinical.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.AllergyIntolerance;
import org.hl7.fhir.r4.model.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ca.uhn.fhir.model.api.Include;
import ca.uhn.fhir.rest.param.DateRangeParam;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.param.UriParam;
import vn.ehealth.hl7.fhir.clinical.entity.AllergyIntoleranceEntity;
import vn.ehealth.hl7.fhir.clinical.entity.ProcedureEntity;
import vn.ehealth.hl7.fhir.core.entity.BaseResource;
import vn.ehealth.hl7.fhir.core.util.ConstantKeys;
import vn.ehealth.hl7.fhir.dao.BaseDao;
import vn.ehealth.hl7.fhir.dao.util.DatabaseUtil;

@Repository
public class AllergyIntoleranceDao extends BaseDao<AllergyIntoleranceEntity, AllergyIntolerance> {

	@Override
	protected String getProfile() {
		return "AllergyIntolerance-v1.0";
	}

	@Override
	protected AllergyIntoleranceEntity fromFhir(AllergyIntolerance obj) {
		return AllergyIntoleranceEntity.from(obj);
	}

	@Override
	protected AllergyIntolerance toFhir(AllergyIntoleranceEntity ent) {
		return AllergyIntoleranceEntity.to(ent);
	}

	@Override
	protected Class<? extends BaseResource> getEntityClass() {
		return AllergyIntoleranceEntity.class;
	}

	@SuppressWarnings("deprecation")
	public List<IBaseResource> search(TokenParam active, TokenParam encounter, ReferenceParam asserter,
			TokenParam category, TokenParam clinicalStatus, TokenParam code, TokenParam criticality,
			DateRangeParam date, TokenParam identifier, DateRangeParam lastDate, TokenParam manifestation,
			DateRangeParam onset, ReferenceParam patient, ReferenceParam recorder, TokenParam route,
			TokenParam severity, TokenParam type, TokenParam verificationStatus,
			// COMMON PARAMS
			TokenParam resid, DateRangeParam _lastUpdated, TokenParam _tag, UriParam _profile, TokenParam _query,
			TokenParam _security, StringParam _content, StringParam _page, String sortParam, Integer count,
			Set<Include> includes) {
		List<IBaseResource> resources = new ArrayList<IBaseResource>();
		Criteria criteria = setParamToCriteria(active, encounter, asserter, category, clinicalStatus, code, criticality,
				date, identifier, lastDate, manifestation, onset, patient, recorder, route, severity, type,
				verificationStatus,
				// COMMON PARAMS
				resid, _lastUpdated, _tag, _profile, _query, _security, _content);
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
		} else {
			query.with(new Sort(Sort.Direction.DESC, "resUpdated"));
			query.with(new Sort(Sort.Direction.DESC, "resCreated"));
		}
		List<AllergyIntoleranceEntity> entitys = mongo.find(query, AllergyIntoleranceEntity.class);
		if (entitys != null) {
			for (AllergyIntoleranceEntity item : entitys) {
				AllergyIntolerance obj = transform(item);

				// add more Resource as it's references
				if (includes != null && includes.size() > 0 && includes.contains(new Include("*"))) {
					if (obj.getPatient() != null) {
						Resource nested = DatabaseUtil.getResourceFromReference(obj.getPatient());
						if (nested != null) {
							obj.getPatient().setResource(nested);
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
				} else {
					if (includes != null && includes.size() > 0
							&& includes.contains(new Include("AllergyIntorance:patient")) && obj.getPatient() != null) {
						Resource nested = DatabaseUtil.getResourceFromReference(obj.getPatient());
						if (nested != null) {
							obj.getPatient().setResource(nested);
//							if (!FPUtil.anyMatch(resources, x -> nested.getId().equals(x.getIdElement().getValue())))
//								resources.add(nested);
						}
					}
					if (includes != null && includes.size() > 0
							&& includes.contains(new Include("AllergyIntorance:encounter"))
							&& obj.getEncounter() != null) {
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

	public long countMatchesAdvancedTotal(TokenParam active, TokenParam encounter, ReferenceParam asserter,
			TokenParam category, TokenParam clinicalStatus, TokenParam code, TokenParam criticality,
			DateRangeParam date, TokenParam identifier, DateRangeParam lastDate, TokenParam manifestation,
			DateRangeParam onset, ReferenceParam patient, ReferenceParam recorder, TokenParam route,
			TokenParam severity, TokenParam type, TokenParam verificationStatus,
			// COMMON PARAMS
			TokenParam resid, DateRangeParam _lastUpdated, TokenParam _tag, UriParam _profile, TokenParam _query,
			TokenParam _security, StringParam _content) {
		long total = 0;
		Criteria criteria = setParamToCriteria(active, encounter, asserter, category, clinicalStatus, code, criticality,
				date, identifier, lastDate, manifestation, onset, patient, recorder, route, severity, type,
				verificationStatus,
				// COMMON PARAMS
				resid, _lastUpdated, _tag, _profile, _query, _security, _content);
		Query query = new Query();
		if (criteria != null) {
			query = Query.query(criteria);
		}
		total = mongo.count(query, ProcedureEntity.class);
		return total;
	}

	private Criteria setParamToCriteria(TokenParam active, TokenParam encounter, ReferenceParam asserter,
			TokenParam category, TokenParam clinicalStatus, TokenParam code, TokenParam criticality,
			DateRangeParam date, TokenParam identifier, DateRangeParam lastDate, TokenParam manifestation,
			DateRangeParam onset, ReferenceParam patient, ReferenceParam recorder, TokenParam route,
			TokenParam severity, TokenParam type, TokenParam verificationStatus,
			// COMMON PARAMS
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
//		// category
//		if (category != null) {
//			criteria.and("category.coding.code.myStringValue").is(category.getValue());
//		}
		// code
		if (code != null) {
			criteria.and("code.coding.code.myStringValue").is(code.getValue());
		}
		// clinicalStatus
		if (clinicalStatus != null) {
			criteria.and("clinicalStatus.coding.code.myStringValue").is(clinicalStatus.getValue());
		}
		// verificationStatus
		if (verificationStatus != null) {
			criteria.and("verificationStatus.coding.code.myStringValue").is(verificationStatus.getValue());
		}
		// manifestation
		if (manifestation != null) {
			criteria.and("manifestation.coding.code.myStringValue").is(manifestation.getValue());
		}
		if (date != null) {
			criteria = DatabaseUtil.setTypeDateToCriteria(criteria, "recordedDate", date);
		}
		if (lastDate != null) {
			criteria = DatabaseUtil.setTypeDateToCriteria(criteria, "lastOccurrence", lastDate);
		}
		if (onset != null) {
			criteria = DatabaseUtil.setTypeDateToCriteria(criteria, "reaction.onset", onset);
		}
		// encounter
		if (encounter != null) {
			if (encounter.getValue().indexOf("|") == -1) {
				criteria.orOperator(Criteria.where("encounter.reference").is(encounter.getValue()),
						Criteria.where("encounter.display").is(encounter.getValue()));
			} else {
				String[] ref = encounter.getValue().split("\\|");
				criteria.and("encounter.identifier.system").is(ref[0]).and("encounter.identifier.value").is(ref[1]);
			}
		}
		// patient
		if (patient != null) {
			if (patient.getValue().indexOf("|") == -1) {
				criteria.orOperator(Criteria.where("subject.reference").is(patient.getValue()),
						Criteria.where("subject.display").is(patient.getValue()));
			} else {
				String[] ref = patient.getValue().split("\\|");
				criteria.and("subject.identifier.system").is(ref[0]).and("subject.identifier.value").is(ref[1]);
			}
		}
		// asserter
		if (asserter != null) {
			if (asserter.getValue().indexOf("|") == -1) {
				criteria.orOperator(Criteria.where("asserter.reference").is(asserter.getValue()),
						Criteria.where("asserter.display").is(asserter.getValue()));
			} else {
				String[] ref = asserter.getValue().split("\\|");
				criteria.and("asserter.identifier.system").is(ref[0]).and("asserter.identifier.value").is(ref[1]);
			}
		}
		// recorder
		if (recorder != null) {
			if (recorder.getValue().indexOf("|") == -1) {
				criteria.orOperator(Criteria.where("recorder.reference").is(recorder.getValue()),
						Criteria.where("recorder.display").is(recorder.getValue()));
			} else {
				String[] ref = recorder.getValue().split("\\|");
				criteria.and("recorder.identifier.system").is(ref[0]).and("recorder.identifier.value").is(ref[1]);
			}
		}
		// patient
		if (patient != null) {
			if (patient.getValue().indexOf("|") == -1) {
				criteria.orOperator(Criteria.where("patient.reference").is(patient.getValue()),
						Criteria.where("patient.display").is(patient.getValue()));
			} else {
				String[] ref = patient.getValue().split("\\|");
				criteria.and("patient.identifier.system").is(ref[0]).and("patient.identifier.value").is(ref[1]);
			}
		}
		return criteria;
	}

}