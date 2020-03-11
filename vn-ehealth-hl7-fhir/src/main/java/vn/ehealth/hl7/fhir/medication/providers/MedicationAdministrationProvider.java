package vn.ehealth.hl7.fhir.medication.providers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.MedicationAdministration;
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
import vn.ehealth.hl7.fhir.core.common.OperationOutcomeException;
import vn.ehealth.hl7.fhir.core.common.OperationOutcomeFactory;
import vn.ehealth.hl7.fhir.core.util.ConstantKeys;
import vn.ehealth.hl7.fhir.medication.dao.IMedicationAdministration;

@Component
public class MedicationAdministrationProvider implements IResourceProvider {
    @Autowired
    FhirContext fhirContext;

    @Autowired
    IMedicationAdministration medicationAdministrationDao;

    private static final Logger log = LoggerFactory.getLogger(MedicationAdministrationProvider.class);

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return MedicationAdministration.class;
    }

    @Create
    public MethodOutcome createMedicationAdministration(HttpServletRequest theRequest,
            @ResourceParam MedicationAdministration obj) {

        log.debug("Create MedicationAdministration Provider called");

        MethodOutcome method = new MethodOutcome();
        method.setCreated(true);
        OperationOutcome opOutcome = new OperationOutcome();
        method.setOperationOutcome(opOutcome);
        MedicationAdministration mongoMedicationAdministration = null;
        try {
            mongoMedicationAdministration = medicationAdministrationDao.create(fhirContext, obj);
            List<String> myString = new ArrayList<>();
            myString.add("MedicationAdministration/" + mongoMedicationAdministration.getIdElement());
            method.setOperationOutcome(OperationOutcomeFactory.createOperationOutcome("Create succsess",
                    "urn:uuid: " + mongoMedicationAdministration.getId(), IssueSeverity.INFORMATION,
                    IssueType.INCOMPLETE, myString));
            method.setId(mongoMedicationAdministration.getIdElement());
            method.setResource(mongoMedicationAdministration);
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
    public MedicationAdministration readMedicationAdministration(HttpServletRequest request,
            @IdParam IdType internalId) {

        MedicationAdministration object = medicationAdministrationDao.read(fhirContext, internalId);
        if (object == null) {
            throw OperationOutcomeFactory.buildOperationOutcomeException(
                    new ResourceNotFoundException("No MedicationAdministration/" + internalId.getIdPart()),
                    OperationOutcome.IssueSeverity.ERROR, OperationOutcome.IssueType.NOTFOUND);
        }
        return object;
    }

    /**
     * @author sonvt
     * @param request
     * @param idType
     * @return read object version
     */
    @Read(version = true)
    public MedicationAdministration readVread(HttpServletRequest request, @IdParam IdType idType) {
        MedicationAdministration object = new MedicationAdministration();
        if (idType.hasVersionIdPart()) {
            object = medicationAdministrationDao.readOrVread(fhirContext, idType);
        } else {
            object = medicationAdministrationDao.read(fhirContext, idType);
        }
        if (object == null) {
            throw OperationOutcomeFactory.buildOperationOutcomeException(
                    new ResourceNotFoundException("No MedicationAdministration/" + idType.getIdPart()),
                    OperationOutcome.IssueSeverity.ERROR, OperationOutcome.IssueType.NOTFOUND);
        }
        return object;
    }

    @Search
    public List<Resource> searchMedicationAdministration(HttpServletRequest request,
            @OptionalParam(name = ConstantKeys.SP_ACTIVE) TokenParam active,
            @OptionalParam(name = ConstantKeys.SP_CODE) TokenParam code,
            @OptionalParam(name = ConstantKeys.SP_CONTEXT) ReferenceParam context,
            @OptionalParam(name = ConstantKeys.SP_DEVICE) ReferenceParam device,
            @OptionalParam(name = ConstantKeys.SP_EFFECTIVE_TIME) DateRangeParam effectiveTime,
            @OptionalParam(name = ConstantKeys.SP_IDENTIFIER) TokenParam identifier,
            @OptionalParam(name = ConstantKeys.SP_MEDICATION) ReferenceParam medication,
            @OptionalParam(name = ConstantKeys.SP_NOT_GIVEN) TokenParam notGiven,
            @OptionalParam(name = ConstantKeys.SP_PATIENT) ReferenceParam patient,
            @OptionalParam(name = ConstantKeys.SP_PERFORMER) ReferenceParam performer,
            @OptionalParam(name = ConstantKeys.SP_PRESCRIPTION) ReferenceParam prescription,
            @OptionalParam(name = ConstantKeys.SP_REASON_GIVEN) TokenParam reasonGiven,
            @OptionalParam(name = ConstantKeys.SP_REASON_NOT_GIVEN) TokenParam reasonNotGiven,
            @OptionalParam(name = ConstantKeys.SP_STATUS) TokenParam status,
            @OptionalParam(name = ConstantKeys.SP_SUBJECT) ReferenceParam subject,
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
                List<Resource> results = medicationAdministrationDao.search(fhirContext, active, code, context, device,
                        effectiveTime, identifier, medication, notGiven, patient, performer, prescription, reasonGiven,
                        reasonNotGiven, status, subject, resid, _lastUpdated, _tag, _profile, _query, _security,
                        _content, _page, sortParam, count);
                return results;
            }
            List<Resource> results = medicationAdministrationDao.search(fhirContext, active, code, context, device,
                    effectiveTime, identifier, medication, notGiven, patient, performer, prescription, reasonGiven,
                    reasonNotGiven, status, subject, resid, _lastUpdated, _tag, _profile, _query, _security, _content,
                    _page, null, count);
            return results;
        }
    }

    @Delete
    public MedicationAdministration deleteMedicationAdministration(HttpServletRequest request,
            @IdParam IdType internalId) {
        MedicationAdministration obj = medicationAdministrationDao.remove(fhirContext, internalId);
        if (obj == null) {
            log.error("Couldn't delete MedicationAdministration" + internalId);
            throw OperationOutcomeFactory.buildOperationOutcomeException(
                    new ResourceNotFoundException("MedicationAdministration is not exit"),
                    OperationOutcome.IssueSeverity.ERROR, OperationOutcome.IssueType.INFORMATIONAL);
        }
        return obj;
    }

    @Update
    public MethodOutcome updateMedicationAdministration(@IdParam IdType theId,
            @ResourceParam MedicationAdministration patient) {

        log.debug("Update MedicationAdministration Provider called");

        MethodOutcome method = new MethodOutcome();
        method.setCreated(true);
        OperationOutcome opOutcome = new OperationOutcome();
        method.setOperationOutcome(opOutcome);
        MedicationAdministration newMedicationAdministration = null;
        try {
            newMedicationAdministration = medicationAdministrationDao.update(fhirContext, patient, theId);
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
                "urn:uuid: " + newMedicationAdministration.getId(), IssueSeverity.INFORMATION, IssueType.INCOMPLETE));
        method.setId(newMedicationAdministration.getIdElement());
        method.setResource(newMedicationAdministration);
        return method;
    }

    @Operation(name = "$total", idempotent = true)
    public Parameters getTotal(HttpServletRequest request,
            @OptionalParam(name = ConstantKeys.SP_ACTIVE) TokenParam active,
            @OptionalParam(name = ConstantKeys.SP_CODE) TokenParam code,
            @OptionalParam(name = ConstantKeys.SP_CONTEXT) ReferenceParam context,
            @OptionalParam(name = ConstantKeys.SP_DEVICE) ReferenceParam device,
            @OptionalParam(name = ConstantKeys.SP_EFFECTIVE_TIME) DateRangeParam effectiveTime,
            @OptionalParam(name = ConstantKeys.SP_IDENTIFIER) TokenParam identifier,
            @OptionalParam(name = ConstantKeys.SP_MEDICATION) ReferenceParam medication,
            @OptionalParam(name = ConstantKeys.SP_NOT_GIVEN) TokenParam notGiven,
            @OptionalParam(name = ConstantKeys.SP_PATIENT) ReferenceParam patient,
            @OptionalParam(name = ConstantKeys.SP_PERFORMER) ReferenceParam performer,
            @OptionalParam(name = ConstantKeys.SP_PRESCRIPTION) ReferenceParam prescription,
            @OptionalParam(name = ConstantKeys.SP_REASON_GIVEN) TokenParam reasonGiven,
            @OptionalParam(name = ConstantKeys.SP_REASON_NOT_GIVEN) TokenParam reasonNotGiven,
            @OptionalParam(name = ConstantKeys.SP_STATUS) TokenParam status,
            @OptionalParam(name = ConstantKeys.SP_SUBJECT) ReferenceParam subject,
            @OptionalParam(name = ConstantKeys.SP_RES_ID) TokenParam resid,
            @OptionalParam(name = ConstantKeys.SP_LAST_UPDATE) DateRangeParam _lastUpdated,
            @OptionalParam(name = ConstantKeys.SP_TAG) TokenParam _tag,
            @OptionalParam(name = ConstantKeys.SP_PROFILE) UriParam _profile,
            @OptionalParam(name = ConstantKeys.SP_QUERY) TokenParam _query,
            @OptionalParam(name = ConstantKeys.SP_SECURITY) TokenParam _security,
            @OptionalParam(name = ConstantKeys.SP_CONTENT_DEFAULT) StringParam _content) {
        Parameters retVal = new Parameters();
        long total = medicationAdministrationDao.countMatchesAdvancedTotal(fhirContext, active, code, context, device,
                effectiveTime, identifier, medication, notGiven, patient, performer, prescription, reasonGiven,
                reasonNotGiven, status, subject, resid, _lastUpdated, _tag, _profile, _query, _security, _content);
        retVal.addParameter().setName("total").setValue(new StringType(String.valueOf(total)));
        return retVal;
    }
}
