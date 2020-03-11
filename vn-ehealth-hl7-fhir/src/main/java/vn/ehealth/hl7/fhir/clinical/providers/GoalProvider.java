package vn.ehealth.hl7.fhir.clinical.providers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hl7.fhir.r4.model.Goal;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.OperationOutcome.IssueSeverity;
import org.hl7.fhir.r4.model.OperationOutcome.IssueType;
import org.hl7.fhir.r4.model.Parameters;
import org.hl7.fhir.r4.model.Resource;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.annotation.Count;
import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.Delete;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Operation;
import ca.uhn.fhir.rest.annotation.OptionalParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.annotation.Sort;
import ca.uhn.fhir.rest.annotation.Update;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.api.SortSpec;
import ca.uhn.fhir.rest.param.DateRangeParam;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.param.UriParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import vn.ehealth.hl7.fhir.clinical.dao.IGoal;
import vn.ehealth.hl7.fhir.core.common.OperationOutcomeException;
import vn.ehealth.hl7.fhir.core.common.OperationOutcomeFactory;
import vn.ehealth.hl7.fhir.core.util.ConstantKeys;

@Component
public class GoalProvider implements IResourceProvider {
    @Autowired
    FhirContext fhirContext;

    @Autowired
    IGoal goalDao;

    private static final Logger log = LoggerFactory.getLogger(GoalProvider.class);

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Goal.class;
    }

    @Create
    public MethodOutcome createGoal(HttpServletRequest theRequest, @ResourceParam Goal obj) {

        log.debug("Create Goal Provider called");

        MethodOutcome method = new MethodOutcome();
        method.setCreated(true);
        Goal mongoGoal = null;
        try {
            mongoGoal = goalDao.create(fhirContext, obj);
            List<String> myString = new ArrayList<>();
            myString.add("Goal/" + mongoGoal.getIdElement());
            method.setOperationOutcome(OperationOutcomeFactory.createOperationOutcome("Create succsess",
                    "urn:uuid: " + mongoGoal.getId(), IssueSeverity.INFORMATION, IssueType.INCOMPLETE, myString));
            method.setId(mongoGoal.getIdElement());
            method.setResource(mongoGoal);
        } catch (Exception ex) {
            if (ex instanceof OperationOutcomeException) {
                OperationOutcomeException outcomeException = (OperationOutcomeException) ex;
                method.setOperationOutcome(outcomeException.getOutcome());
                method.setCreated(false);
            } else {
                log.error(ex.getMessage());
                method.setCreated(false);
                method.setOperationOutcome(OperationOutcomeFactory.createOperationOutcome(ex.getMessage()));
            }
        }
        return method;
    }

    @Read
    public Goal readGoal(HttpServletRequest request, @IdParam IdType internalId) {

        Goal object = goalDao.read(fhirContext, internalId);
        if (object == null) {
            throw OperationOutcomeFactory.buildOperationOutcomeException(
                    new ResourceNotFoundException("No Goal/" + internalId.getIdPart()),
                    OperationOutcome.IssueSeverity.ERROR, OperationOutcome.IssueType.NOTFOUND);
        }
        return object;
    }

    @Delete
    public Goal deleteGoal(HttpServletRequest request, @IdParam IdType internalId) {
        Goal object = goalDao.remove(fhirContext, internalId);
        if (object == null) {
            log.error("Couldn't delete Goal" + internalId);
            throw OperationOutcomeFactory.buildOperationOutcomeException(
                    new ResourceNotFoundException("Goal is not exit"), OperationOutcome.IssueSeverity.ERROR,
                    OperationOutcome.IssueType.INFORMATIONAL);
        }
        return object;
    }

    @Update
    public MethodOutcome updateGoal(@IdParam IdType theId, @ResourceParam Goal patient) {

        log.debug("Update Goal Provider called");

        MethodOutcome method = new MethodOutcome();
        method.setCreated(true);
        OperationOutcome opOutcome = new OperationOutcome();
        method.setOperationOutcome(opOutcome);
        Goal newGoal = null;
        try {
            newGoal = goalDao.update(fhirContext, patient, theId);
        } catch (Exception ex) {
            if (ex instanceof OperationOutcomeException) {
                OperationOutcomeException outcomeException = (OperationOutcomeException) ex;
                method.setOperationOutcome(outcomeException.getOutcome());
                method.setCreated(false);
            } else {
                log.error(ex.getMessage());
                method.setCreated(false);
                method.setOperationOutcome(OperationOutcomeFactory.createOperationOutcome(ex.getMessage()));
            }
        }
        method.setOperationOutcome(OperationOutcomeFactory.createOperationOutcome("Update succsess",
                "urn:uuid: " + newGoal.getId(), IssueSeverity.INFORMATION, IssueType.INCOMPLETE));
        method.setId(newGoal.getIdElement());
        method.setResource(newGoal);
        return method;
    }

    /**
     * @author sonvt
     * @param request
     * @param idType
     * @return read object version
     */
    @Read(version = true)
    public Goal readVread(HttpServletRequest request, @IdParam IdType idType) {
        Goal object = new Goal();
        if (idType.hasVersionIdPart()) {
            object = goalDao.readOrVread(fhirContext, idType);
        } else {
            object = goalDao.read(fhirContext, idType);
        }
        if (object == null) {
            throw OperationOutcomeFactory.buildOperationOutcomeException(
                    new ResourceNotFoundException("No Goal/" + idType.getIdPart()),
                    OperationOutcome.IssueSeverity.ERROR, OperationOutcome.IssueType.NOTFOUND);
        }
        return object;
    }

    @Search
    public List<Resource> searchGoal(HttpServletRequest request,
            @OptionalParam(name = ConstantKeys.SP_ACTIVE) TokenParam active,
            @OptionalParam(name = ConstantKeys.SP_CATEGORY) TokenParam category,
            @OptionalParam(name = ConstantKeys.SP_IDENTIFIER) TokenParam identifier,
            @OptionalParam(name = ConstantKeys.SP_PATIENT) ReferenceParam patient,
            @OptionalParam(name = ConstantKeys.SP_START_DATE) DateRangeParam startDate,
            @OptionalParam(name = ConstantKeys.SP_STATUS) TokenParam status,
            @OptionalParam(name = ConstantKeys.SP_SUBJECT) ReferenceParam subject,
            @OptionalParam(name = ConstantKeys.SP_TARGET_DATE) DateRangeParam targetDate,
            @OptionalParam(name = ConstantKeys.SP_RES_ID) TokenParam resid,
            @OptionalParam(name = ConstantKeys.SP_LAST_UPDATE) DateRangeParam _lastUpdated,
            @OptionalParam(name = ConstantKeys.SP_TAG) TokenParam _tag,
            @OptionalParam(name = ConstantKeys.SP_PROFILE) UriParam _profile,
            @OptionalParam(name = ConstantKeys.SP_QUERY) TokenParam _query,
            @OptionalParam(name = ConstantKeys.SP_SECURITY) TokenParam _security,
            @OptionalParam(name = ConstantKeys.SP_CONTENT_DEFAULT) StringParam _content,
            @OptionalParam(name = ConstantKeys.SP_PAGE) StringParam _page, @Sort SortSpec theSort, @Count Integer count)
            throws OperationOutcomeException {
        if (count != null && count > 50) {
            throw OperationOutcomeFactory.buildOperationOutcomeException(
                    new ResourceNotFoundException("Total is not gre > 50"), OperationOutcome.IssueSeverity.ERROR,
                    OperationOutcome.IssueType.INFORMATIONAL);
        } else {
            if (theSort != null) {
                String sortParam = theSort.getParamName();
                List<Resource> results = goalDao.search(fhirContext, active, category, identifier, patient, startDate,
                        status, subject, targetDate, resid, _lastUpdated, _tag, _profile, _query, _security, _content,
                        _page, sortParam, count);
                return results;
            }
            List<Resource> results = goalDao.search(fhirContext, active, category, identifier, patient, startDate,
                    status, subject, targetDate, resid, _lastUpdated, _tag, _profile, _query, _security, _content,
                    _page, null, count);
            return results;
        }
    }

    @Operation(name = "$total", idempotent = true)
    public Parameters getTotal(HttpServletRequest request,
            @OptionalParam(name = ConstantKeys.SP_ACTIVE) TokenParam active,
            @OptionalParam(name = ConstantKeys.SP_CATEGORY) TokenParam category,
            @OptionalParam(name = ConstantKeys.SP_IDENTIFIER) TokenParam identifier,
            @OptionalParam(name = ConstantKeys.SP_PATIENT) ReferenceParam patient,
            @OptionalParam(name = ConstantKeys.SP_START_DATE) DateRangeParam startDate,
            @OptionalParam(name = ConstantKeys.SP_STATUS) TokenParam status,
            @OptionalParam(name = ConstantKeys.SP_SUBJECT) ReferenceParam subject,
            @OptionalParam(name = ConstantKeys.SP_TARGET_DATE) DateRangeParam targetDate,
            @OptionalParam(name = ConstantKeys.SP_RES_ID) TokenParam resid,
            @OptionalParam(name = ConstantKeys.SP_LAST_UPDATE) DateRangeParam _lastUpdated,
            @OptionalParam(name = ConstantKeys.SP_TAG) TokenParam _tag,
            @OptionalParam(name = ConstantKeys.SP_PROFILE) UriParam _profile,
            @OptionalParam(name = ConstantKeys.SP_QUERY) TokenParam _query,
            @OptionalParam(name = ConstantKeys.SP_SECURITY) TokenParam _security,
            @OptionalParam(name = ConstantKeys.SP_CONTENT_DEFAULT) StringParam _content) {
        Parameters retVal = new Parameters();
        long total = goalDao.countMatchesAdvancedTotal(fhirContext, active, category, identifier, patient, startDate,
                status, subject, targetDate, resid, _lastUpdated, _tag, _profile, _query, _security, _content);
        retVal.addParameter().setName("total").setValue(new StringType(String.valueOf(total)));
        return retVal;
    }
}
