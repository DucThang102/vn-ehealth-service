package vn.ehealth.hl7.fhir.diagnostic.providers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.Parameters;
import org.hl7.fhir.r4.model.Specimen;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IPrimitiveType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.uhn.fhir.model.api.Include;
import ca.uhn.fhir.rest.annotation.Count;
import ca.uhn.fhir.rest.annotation.IncludeParam;
import ca.uhn.fhir.rest.annotation.Operation;
import ca.uhn.fhir.rest.annotation.OptionalParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.annotation.Sort;
import ca.uhn.fhir.rest.api.SortSpec;
import ca.uhn.fhir.rest.api.server.IBundleProvider;
import ca.uhn.fhir.rest.param.DateRangeParam;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.param.UriParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import vn.ehealth.hl7.fhir.providers.BaseController;
import vn.ehealth.hl7.fhir.core.common.OperationOutcomeException;
import vn.ehealth.hl7.fhir.core.common.OperationOutcomeFactory;
import vn.ehealth.hl7.fhir.core.util.ConstantKeys;
import vn.ehealth.hl7.fhir.dao.BaseDao;
import vn.ehealth.hl7.fhir.diagnostic.dao.impl.SpecimenDao;
import vn.ehealth.hl7.fhir.diagnostic.entity.SpecimenEntity;

@Component
public class SpecimenProvider extends BaseController<SpecimenEntity, Specimen> implements IResourceProvider {

	@Autowired
	SpecimenDao specimenDao;

	@Override
	public Class<? extends IBaseResource> getResourceType() {
		return Specimen.class;
	}

	@Search
	public IBundleProvider searchSpecimen(HttpServletRequest _request,
			@OptionalParam(name = Specimen.SP_ACCESSION) TokenParam accession,
			@OptionalParam(name = Specimen.SP_BODYSITE) TokenParam bodysite,
			@OptionalParam(name = Specimen.SP_COLLECTED) DateRangeParam collected,
			@OptionalParam(name = Specimen.SP_COLLECTOR) ReferenceParam collector,
			@OptionalParam(name = Specimen.SP_CONTAINER) TokenParam container,
			@OptionalParam(name = Specimen.SP_CONTAINER_ID) TokenParam containerId,
			@OptionalParam(name = Specimen.SP_IDENTIFIER) TokenParam identifier,
			@OptionalParam(name = Specimen.SP_PARENT) ReferenceParam parent,
			@OptionalParam(name = Specimen.SP_PATIENT) ReferenceParam patient,
			@OptionalParam(name = Specimen.SP_STATUS) TokenParam status,
			@OptionalParam(name = Specimen.SP_SUBJECT) ReferenceParam subject,
			@OptionalParam(name = Specimen.SP_TYPE) TokenParam type,
			@OptionalParam(name = ConstantKeys.SP_REQUEST) ReferenceParam request,
			// COMMON PARAMS
			@OptionalParam(name = ConstantKeys.SP_RES_ID) TokenParam resid,
			@OptionalParam(name = ConstantKeys.SP_LAST_UPDATE) DateRangeParam _lastUpdated,
			@OptionalParam(name = ConstantKeys.SP_TAG) TokenParam _tag,
			@OptionalParam(name = ConstantKeys.SP_PROFILE) UriParam _profile,
			@OptionalParam(name = ConstantKeys.SP_QUERY) TokenParam _query,
			@OptionalParam(name = ConstantKeys.SP_SECURITY) TokenParam _security,
			@OptionalParam(name = ConstantKeys.SP_CONTENT) StringParam _content,
			@OptionalParam(name = ConstantKeys.SP_PAGE) StringParam _page, @Sort SortSpec theSort, @Count Integer count,
			@IncludeParam(allow = { "Specimen:subject", "Specimen:request", "*" }) Set<Include> includes)
			throws OperationOutcomeException {
		if (count != null && count > ConstantKeys.DEFAULT_PAGE_MAX_SIZE) {
			throw OperationOutcomeFactory.buildOperationOutcomeException(
					new ResourceNotFoundException("Can not load more than " + ConstantKeys.DEFAULT_PAGE_MAX_SIZE),
					OperationOutcome.IssueSeverity.ERROR, OperationOutcome.IssueType.NOTSUPPORTED);
		} else {
			List<IBaseResource> results = new ArrayList<>();
			if (theSort != null) {
				String sortParam = theSort.getParamName();
				results = specimenDao.search(fhirContext, request, resid, _lastUpdated, _tag, _profile, _query,
						_security, _content, _page, sortParam, count, includes);
			} else
				results = specimenDao.search(fhirContext, request, resid, _lastUpdated, _tag, _profile, _query,
						_security, _content, _page, null, count, includes);
			// final List<IBaseResource> finalResults = DataConvertUtil.transform(results, x
			// -> x);
			final List<IBaseResource> finalResults = results;

			return new IBundleProvider() {

				@Override
				public Integer size() {
					return Integer.parseInt(String.valueOf(specimenDao.countMatchesAdvancedTotal(fhirContext, request,
							resid, _lastUpdated, _tag, _profile, _query, _security, _content)));
				}

				@Override
				public Integer preferredPageSize() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public String getUuid() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public List<IBaseResource> getResources(int theFromIndex, int theToIndex) {
					return finalResults;
				}

				@Override
				public IPrimitiveType<Date> getPublished() {
					// TODO Auto-generated method stub
					return null;
				}
			};
		}
	}

	@Operation(name = "$total", idempotent = true)
	public Parameters getTotal(HttpServletRequest _request,
			@OptionalParam(name = ConstantKeys.SP_REQUEST) ReferenceParam request,
			@OptionalParam(name = ConstantKeys.SP_RES_ID) TokenParam resid,
			@OptionalParam(name = ConstantKeys.SP_LAST_UPDATE) DateRangeParam _lastUpdated,
			@OptionalParam(name = ConstantKeys.SP_TAG) TokenParam _tag,
			@OptionalParam(name = ConstantKeys.SP_PROFILE) UriParam _profile,
			@OptionalParam(name = ConstantKeys.SP_QUERY) TokenParam _query,
			@OptionalParam(name = ConstantKeys.SP_SECURITY) TokenParam _security,
			@OptionalParam(name = ConstantKeys.SP_CONTENT) StringParam _content) {
		Parameters retVal = new Parameters();
		long total = specimenDao.countMatchesAdvancedTotal(fhirContext, request, resid, _lastUpdated, _tag, _profile,
				_query, _security, _content);
		retVal.addParameter().setName("total").setValue(new StringType(String.valueOf(total)));
		return retVal;
	}

	@Override
	protected BaseDao<SpecimenEntity, Specimen> getDao() {
		return specimenDao;
	}
}
