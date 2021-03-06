package vn.ehealth.hl7.fhir.provider.providers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hl7.fhir.r4.model.Device;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.Parameters;
import org.hl7.fhir.r4.model.Resource;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IPrimitiveType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.uhn.fhir.rest.annotation.Count;
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
import vn.ehealth.hl7.fhir.core.util.DataConvertUtil;
import vn.ehealth.hl7.fhir.dao.BaseDao;
import vn.ehealth.hl7.fhir.provider.dao.impl.DeviceDao;
import vn.ehealth.hl7.fhir.provider.entity.DeviceEntity;

@Component
public class DeviceProvider extends BaseController<DeviceEntity, Device> implements IResourceProvider {

	@Autowired
	DeviceDao deviceDao;

	@Override
	public Class<? extends IBaseResource> getResourceType() {
		return Device.class;
	}

	@Search
	public IBundleProvider searchDevice(HttpServletRequest request,
			@OptionalParam(name = "device-name") StringParam deviceName,
			@OptionalParam(name = ConstantKeys.SP_IDENTIFIER) TokenParam identifier,
			@OptionalParam(name = "location") ReferenceParam location,
			@OptionalParam(name = "manufacturer") StringParam manufacturer,
			@OptionalParam(name = "model") StringParam model,
			@OptionalParam(name = "organization") ReferenceParam organization,
			@OptionalParam(name = "patient") ReferenceParam patient,
			@OptionalParam(name = "udi-carrier") StringParam udiCarrier,
			@OptionalParam(name = "udi-di") StringParam udiDi, @OptionalParam(name = "url") UriParam url,
			@OptionalParam(name = "status") TokenParam status, @OptionalParam(name = "type") TokenParam type,
			@OptionalParam(name = ConstantKeys.SP_RES_ID) TokenParam resid,
			@OptionalParam(name = ConstantKeys.SP_LAST_UPDATE) DateRangeParam _lastUpdated,
			@OptionalParam(name = ConstantKeys.SP_TAG) TokenParam _tag,
			@OptionalParam(name = ConstantKeys.SP_PROFILE) UriParam _profile,
			@OptionalParam(name = ConstantKeys.SP_QUERY) TokenParam _query,
			@OptionalParam(name = ConstantKeys.SP_SECURITY) TokenParam _security,
			@OptionalParam(name = ConstantKeys.SP_CONTENT) StringParam _content,
			@OptionalParam(name = ConstantKeys.SP_PAGE) StringParam _page, @Sort SortSpec theSort, @Count Integer count)
			throws OperationOutcomeException {
		if (count != null && count > ConstantKeys.DEFAULT_PAGE_MAX_SIZE) {
			throw OperationOutcomeFactory.buildOperationOutcomeException(
					new ResourceNotFoundException("Can not load more than " + ConstantKeys.DEFAULT_PAGE_MAX_SIZE),
					OperationOutcome.IssueSeverity.ERROR, OperationOutcome.IssueType.NOTSUPPORTED);
		} else {
			List<Resource> results = new ArrayList<Resource>();
			if (theSort != null) {
				String sortParam = theSort.getParamName();
				results = deviceDao.search(fhirContext, deviceName, identifier, location, manufacturer, model,
						organization, patient, udiCarrier, udiDi, url, status, type, resid, _lastUpdated, _tag,
						_profile, _query, _security, _content, _page, sortParam, count);
			} else
				results = deviceDao.search(fhirContext, deviceName, identifier, location, manufacturer, model,
						organization, patient, udiCarrier, udiDi, url, status, type, resid, _lastUpdated, _tag,
						_profile, _query, _security, _content, _page, "", count);
			final List<IBaseResource> finalResults = DataConvertUtil.transform(results, x -> x);

			return new IBundleProvider() {

				@Override
				public Integer size() {
					return Integer.parseInt(String.valueOf(deviceDao.findMatchesAdvancedTotal(fhirContext, deviceName,
							identifier, location, manufacturer, model, organization, patient, udiCarrier, udiDi, url,
							status, type, resid, _lastUpdated, _tag, _profile, _query, _security, _content)));
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
	public Parameters findMatchesAdvancedTotal(HttpServletRequest request,
			@OptionalParam(name = "device-name") StringParam deviceName,
			@OptionalParam(name = ConstantKeys.SP_IDENTIFIER) TokenParam identifier,
			@OptionalParam(name = "location") ReferenceParam location,
			@OptionalParam(name = "manufacturer") StringParam manufacturer,
			@OptionalParam(name = "model") StringParam model,
			@OptionalParam(name = "organization") ReferenceParam organization,
			@OptionalParam(name = "patient") ReferenceParam patient,
			@OptionalParam(name = "udi-carrier") StringParam udiCarrier,
			@OptionalParam(name = "udi-di") StringParam udiDi, @OptionalParam(name = "url") UriParam url,
			@OptionalParam(name = "status") TokenParam status, @OptionalParam(name = "type") TokenParam type,
			@OptionalParam(name = ConstantKeys.SP_RES_ID) TokenParam resid,
			@OptionalParam(name = ConstantKeys.SP_LAST_UPDATE) DateRangeParam _lastUpdated,
			@OptionalParam(name = ConstantKeys.SP_TAG) TokenParam _tag,
			@OptionalParam(name = ConstantKeys.SP_PROFILE) UriParam _profile,
			@OptionalParam(name = ConstantKeys.SP_QUERY) TokenParam _query,
			@OptionalParam(name = ConstantKeys.SP_SECURITY) TokenParam _security,
			@OptionalParam(name = ConstantKeys.SP_CONTENT) StringParam _content) {
		Parameters retVal = new Parameters();
		long total = deviceDao.findMatchesAdvancedTotal(fhirContext, deviceName, identifier, location, manufacturer,
				model, organization, patient, udiCarrier, udiDi, url, status, type, resid, _lastUpdated, _tag, _profile,
				_query, _security, _content);
		retVal.addParameter().setName("total").setValue(new StringType(String.valueOf(total)));
		return retVal;
	}

	@Override
	protected BaseDao<DeviceEntity, Device> getDao() {
		return deviceDao;
	}
}
