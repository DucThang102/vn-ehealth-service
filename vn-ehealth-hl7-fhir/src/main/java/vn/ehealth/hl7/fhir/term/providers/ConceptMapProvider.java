package vn.ehealth.hl7.fhir.term.providers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.ConceptMap;
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
import ca.uhn.fhir.rest.annotation.OperationParam;
import ca.uhn.fhir.rest.annotation.OptionalParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.annotation.Sort;
import ca.uhn.fhir.rest.annotation.Update;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.api.SortSpec;
import ca.uhn.fhir.rest.param.DateRangeParam;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.param.UriParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import vn.ehealth.hl7.fhir.core.common.OperationOutcomeException;
import vn.ehealth.hl7.fhir.core.common.OperationOutcomeFactory;
import vn.ehealth.hl7.fhir.core.util.ConstantKeys;
import vn.ehealth.hl7.fhir.term.dao.IConceptMap;

/**
 * @author SONVT24
 * @since 2019
 * @version 1.0
 */
@Component
public class ConceptMapProvider implements IResourceProvider {
    @Autowired
    FhirContext fhirContext;

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return ConceptMap.class;
    }

    @Autowired
    IConceptMap conceptMapDao;

    private static final Logger log = LoggerFactory.getLogger(ConceptMapProvider.class);

    @Create
    public MethodOutcome create(HttpServletRequest theRequest, @ResourceParam ConceptMap object) {
        log.debug("Create ConceptMap Provider called");
        // String permissionAccept = TerminologyOauth2Keys.ConceptMapOauth2.C_MAP_ADD;
        // OAuth2Util.checkOauth2(theRequest, permissionAccept);
        MethodOutcome method = new MethodOutcome();
        method.setCreated(true);
        OperationOutcome opOutcome = new OperationOutcome();

        method.setOperationOutcome(opOutcome);
        ConceptMap mongoObj = null;
        try {
            mongoObj = conceptMapDao.create(fhirContext, object);
            List<String> myString = new ArrayList<>();
            myString.add("ConceptMap/" + mongoObj.getIdElement());
            method.setOperationOutcome(OperationOutcomeFactory.createOperationOutcome("Create succsess",
                    "urn:uuid: " + mongoObj.getId(), IssueSeverity.INFORMATION, IssueType.INCOMPLETE, myString));
            method.setId(mongoObj.getIdElement());
            method.setResource(mongoObj);
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
    public ConceptMap readConceptMap(HttpServletRequest request, @IdParam IdType internalId) {
        log.debug("Read ConceptMap Provider called");
        // String permissionAccept = TerminologyOauth2Keys.ConceptMapOauth2.C_MAP_VIEW;
        // OAuth2Util.checkOauth2(request, permissionAccept);
        ConceptMap object = conceptMapDao.read(fhirContext, internalId);

        if (object == null) {
            throw OperationOutcomeFactory.buildOperationOutcomeException(
                    new ResourceNotFoundException("No ConceptMap/" + internalId.getIdPart()),
                    OperationOutcome.IssueSeverity.ERROR, OperationOutcome.IssueType.NOTFOUND);
        }
        return object;
    }
    
    /**
     * @author sonvt
     * @param request
     * @param idType
     * @return
     * read object version
     */
    @Read(version=true)
    public ConceptMap readVread(HttpServletRequest request, @IdParam IdType idType) {
        ConceptMap object = new ConceptMap();
        if (idType.hasVersionIdPart()) {
            object = conceptMapDao.readOrVread(fhirContext, idType);
        }else {
            object = conceptMapDao.read(fhirContext, idType);
        }
        if (object == null) {
            throw OperationOutcomeFactory.buildOperationOutcomeException(
                    new ResourceNotFoundException("No ConceptMap/" + idType.getIdPart()),
                    OperationOutcome.IssueSeverity.ERROR, OperationOutcome.IssueType.NOTFOUND);
        }
        return object;
    }

    @Search
    public List<Resource> searchConceptMap(HttpServletRequest request,
            @OptionalParam(name = ConstantKeys.SP_ACTIVE) TokenParam active,
            @OptionalParam(name = ConstantKeys.SP_DATE) DateRangeParam date,
            @OptionalParam(name = ConstantKeys.SP_DEPENDSON) UriParam dependson,
            @OptionalParam(name = ConstantKeys.SP_DESCRIPTION) StringParam description,
            @OptionalParam(name = ConstantKeys.SP_IDENTIFIER) TokenParam identifier,
            @OptionalParam(name = ConstantKeys.SP_JURIS) TokenParam jurisdiction,
            @OptionalParam(name = ConstantKeys.SP_NAME) StringParam name,
            @OptionalParam(name = ConstantKeys.SP_OTHER) UriParam other,
            @OptionalParam(name = ConstantKeys.SP_PRODUCT) UriParam product,
            @OptionalParam(name = ConstantKeys.SP_PUBLISHER) StringParam publisher,
            @OptionalParam(name = ConstantKeys.SP_CODE) TokenParam code,
            @OptionalParam(name = ConstantKeys.SP_SOURCE_SYSTEM) UriParam source,
            @OptionalParam(name = ConstantKeys.SP_STATUS) TokenParam status,
            @OptionalParam(name = ConstantKeys.SP_TARGET) UriParam target,
            @OptionalParam(name = ConstantKeys.SP_TITLE) StringParam title,
            @OptionalParam(name = ConstantKeys.SP_URL) UriParam url,
            @OptionalParam(name = ConstantKeys.SP_VERSION) TokenParam version,
            // Parameters for all resources
            @OptionalParam(name = ConstantKeys.SP_RES_ID) TokenParam resid,
            @OptionalParam(name = "_lastUpdated") DateRangeParam _lastUpdated,
            @OptionalParam(name = "_tag") TokenParam _tag, @OptionalParam(name = "_profile") UriParam _profile,
            @OptionalParam(name = "_query") TokenParam _query, @OptionalParam(name = "_security") TokenParam _security,
            @OptionalParam(name = "_content") StringParam _content,
            // Search result parameters
            @OptionalParam(name = ConstantKeys.SP_PAGE) StringParam _page, @Sort SortSpec theSort, @Count Integer count)
            throws OperationOutcomeException {
        log.debug("search ConceptMap Provider called");
        if (count != null && count > 50) {
            throw OperationOutcomeFactory.buildOperationOutcomeException(
                    new ResourceNotFoundException("Total is not gre > 50"), OperationOutcome.IssueSeverity.ERROR,
                    OperationOutcome.IssueType.INFORMATIONAL);
        } else {
            if (theSort != null) {
                String sortParam = theSort.getParamName();
                List<Resource> results = conceptMapDao.search(fhirContext, active, date, dependson, description,
                        identifier, jurisdiction, name, other, product, publisher, code, source, status, target, title,
                        url, version, resid, _lastUpdated, _tag, _profile, _query, _security, _content, _page,
                        sortParam, count);
                return results;
            }
            List<Resource> results = conceptMapDao.search(fhirContext, active, date, dependson, description, identifier,
                    jurisdiction, name, other, product, publisher, code, source, status, target, title, url, version,
                    resid, _lastUpdated, _tag, _profile, _query, _security, _content, _page, null, count);
            return results;
        }
    }

    @Delete
    public ConceptMap deleteConceptMap(HttpServletRequest request, @IdParam IdType internalId) {
        log.debug("delete ConceptMap Provider called");
        // String permissionAccept =
        // TerminologyOauth2Keys.ConceptMapOauth2.C_MAP_DELETE;
        // OAuth2Util.checkOauth2(request, permissionAccept);
        ConceptMap conceptMap = conceptMapDao.remove(fhirContext, internalId);
        if (conceptMap == null) {
            log.error("Couldn't delete ConceptMap" + internalId);
            throw OperationOutcomeFactory.buildOperationOutcomeException(
                    new ResourceNotFoundException("ConceptMap is not exit"), OperationOutcome.IssueSeverity.ERROR,
                    OperationOutcome.IssueType.INFORMATIONAL);
        }
        return conceptMap;
    }

    @Update
    public MethodOutcome update(HttpServletRequest request, @IdParam IdType idType, @ResourceParam ConceptMap object) {

        log.debug("update ConceptMap Provider called");
        // String permissionAccept = TerminologyOauth2Keys.ConceptMapOauth2.C_MAP_ADD;
        // OAuth2Util.checkOauth2(request, permissionAccept);
        MethodOutcome method = new MethodOutcome();
        method.setCreated(true);
        OperationOutcome opOutcome = new OperationOutcome();
        method.setOperationOutcome(opOutcome);
        ConceptMap newConceptMap = null;
        try {
            newConceptMap = conceptMapDao.update(fhirContext, object, idType);
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
                "urn:uuid:" + newConceptMap.getId(), IssueSeverity.INFORMATION, IssueType.INFORMATIONAL));
        method.setId(newConceptMap.getIdElement());
        method.setResource(newConceptMap);
        return method;
    }

    @Operation(name = "$translate", idempotent = true)
    public Parameters findMatchesAdvanced(@OperationParam(name = "code") TokenParam code,
            @OperationParam(name = "system") UriParam system, @OperationParam(name = "version") StringParam version,
            @OperationParam(name = "source") UriParam source, @OperationParam(name = "coding") Coding coding,
            @OperationParam(name = "target") UriParam target, @OperationParam(name = "reverse") StringParam reverse) {

        Parameters retVal = new Parameters();
        retVal = conceptMapDao.getTranslateParams(code, system, version, source, coding, target, reverse);
        return retVal;
    }

    @Operation(name = "$closure", idempotent = true)
    public ConceptMap getClosureParams(@OperationParam(name = "name") StringParam name,
            @OperationParam(name = "version") StringParam version, @OperationParam(name = "concept") Coding concept) {
        ConceptMap retVal = new ConceptMap();
        retVal = conceptMapDao.getClosureParams(name, version, concept);
        return retVal;
    }

    @Operation(name = "$total", idempotent = true)
    public Parameters findMatchesAdvancedTotal(HttpServletRequest request,
            @OptionalParam(name = ConstantKeys.SP_ACTIVE) TokenParam active,
            @OptionalParam(name = ConstantKeys.SP_DATE) DateRangeParam date,
            @OptionalParam(name = ConstantKeys.SP_DEPENDSON) UriParam dependson,
            @OptionalParam(name = ConstantKeys.SP_DESCRIPTION) StringParam description,
            @OptionalParam(name = ConstantKeys.SP_IDENTIFIER) TokenParam identifier,
            @OptionalParam(name = ConstantKeys.SP_JURIS) TokenParam jurisdiction,
            @OptionalParam(name = ConstantKeys.SP_NAME) StringParam name,
            @OptionalParam(name = ConstantKeys.SP_OTHER) UriParam other,
            @OptionalParam(name = ConstantKeys.SP_PRODUCT) UriParam product,
            @OptionalParam(name = ConstantKeys.SP_PUBLISHER) StringParam publisher,
            @OptionalParam(name = ConstantKeys.SP_CODE) TokenParam code,
            @OptionalParam(name = ConstantKeys.SP_SOURCE_SYSTEM) UriParam source,
            @OptionalParam(name = ConstantKeys.SP_STATUS) TokenParam status,
            @OptionalParam(name = ConstantKeys.SP_TARGET) UriParam target,
            @OptionalParam(name = ConstantKeys.SP_TITLE) StringParam title,
            @OptionalParam(name = ConstantKeys.SP_URL) UriParam url,
            @OptionalParam(name = ConstantKeys.SP_VERSION) TokenParam version,
            // Parameters for all resources
            @OptionalParam(name = ConstantKeys.SP_RES_ID) TokenParam resid,
            @OptionalParam(name = "_lastUpdated") DateRangeParam _lastUpdated,
            @OptionalParam(name = "_tag") TokenParam _tag, @OptionalParam(name = "_profile") UriParam _profile,
            @OptionalParam(name = "_query") TokenParam _query, @OptionalParam(name = "_security") TokenParam _security,
            @OptionalParam(name = "_content") StringParam _content) {
        Parameters retVal = new Parameters();
        long total = conceptMapDao.findMatchesAdvancedTotal(fhirContext, active, date, dependson, description,
                identifier, jurisdiction, name, other, product, publisher, code, source, status, target, title, url,
                version, resid, _lastUpdated, _tag, _profile, _query, _security, _content);
        retVal.addParameter().setName("total").setValue(new StringType(String.valueOf(total)));
        return retVal;
    }
}
