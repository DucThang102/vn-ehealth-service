package vn.ehealth.hl7.fhir.core.util;

public class ConstantKeys {

    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int DEFAULT_PAGE_MAX_SIZE = 100;
    public static final int PAGE = 0;
    public static final int DELTA = 10;
    public static final long LONG_DEFAULT = 0;
    public static final String STRING_DEFAULT = "";
    public static final long SELECT_BOX = -1;
    public static final String TEXT_BOX = STRING_DEFAULT;
    public static final boolean CHECK_BOX = false;
    public static final long TREE_ROOT_ID = -1;
    public static final String NEXT_URL = "nextURL";
    public static final String BACK_URL = "backURL";
    //public static final int DEFAULT_RESOUCE_MAX_SIZE = 50;

    // Maxlenght
    public static final int MAXLENGHT20 = 20;
    public static final int MAXLENGHT200 = 200;
    public static final int MAXLENGHT10 = 10;
    public static final int MAXLENGHT50 = 50;
    public static final int MAXLENGHT500 = 500;
    public static final int MAXLENGHT30 = 30;
    
    // Resource list
    public static final String RES_CAREPLAN = "CarePlan";
    public static final String RES_CLINICAL_IMPRESSION = "ClinicalImpression";
    public static final String RES_CONDITION = "Condition";
    public static final String RES_DETECTED_ISSUE = "DetectedIssue";
    public static final String RES_GOAL = "Goal";
    public static final String RES_PROCEDURE = "Procedure";
    public static final String RES_SERVICE_REQUEST = "ServiceRequest";
    public static final String RES_DIAGNOSTIC_REPORT = "DiagnosticReport";
    public static final String RES_IMAGING_STUDY = "ImagingStudy";
    public static final String RES_OBSERVATION = "Observation";
    public static final String RES_SPECIMEN = "Specimen";
    public static final String RES_CARETEAM = "CareTeam";
    public static final String RES_ENCOUNTER = "Encounter";
    public static final String RES_EPISODEOFCARE = "EpisodeOfCare";
    public static final String RES_MEDICATION_ADMINISTRATION = "MedicationAdministration";
    public static final String RES_MEDICATION = "Medication";
    public static final String RES_MEDICATION_DISPENSE = "MedicationDispense";
    public static final String RES_MEDICATION_REQUEST = "MedicationRequest";
    public static final String RES_MEDICATION_STATEMENT = "MedicationStatement";
    public static final String RES_PATIENT = "Patient";
    public static final String RES_RELATED_PERSON = "RelatedPerson";
    public static final String RES_DEVICE = "Device";
    public static final String RES_HEALTHCARE_SERVICE = "HealthcareService";
    public static final String RES_LOCATION = "Location";
    public static final String RES_ORGANIZATION = "Organization";
    public static final String RES_PRACTITIONER = "Practitioner";
    public static final String RES_PRACTITIONER_ROLE = "PractitionerRole";
    public static final String RES_APPOINTMENT = "Appoinment";
    public static final String RES_APPOINMENT_RESPONSE = "AppoinmentResponse";
    public static final String RES_SCHEDULE = "Schedule";
    public static final String RES_SLOT = "Slot";
    public static final String RES_CODESYSTEM = "CodeSystem";
    public static final String RES_CONCEPTMAP = "ConceptMap";
    public static final String RES_VALUESET = "ValueSet";
    public static final String RES_PERSON = "Person";
    
    // Param list
    public static final String SP_BIRTHDATE = "birthdate";
    public static final String SP_FAMILY = "family";
    public static final String SP_GENDER = "gender";
    public static final String SP_GIVEN = "given";
    public static final String SP_IDENTIFIER = "identifier";
    public static final String SP_NAME = "name";
    public static final String SP_RES_ID = "_id";
    public static final String SP_CODE = "code";
    public static final String SP_CONTENT_MODE = "content-mode";
    public static final String SP_DATE = "date";
    public static final String SP_DESCRIPTION = "description";
    public static final String SP_JURIS = "jurisdiction";
    public static final String SP_LANGUAGE = "language";
    public static final String SP_PUBLISHER = "publisher";
    public static final String SP_STATUS = "status";
    public static final String SP_SYSTEM = "system";
    public static final String SP_TITLE = "title";
    public static final String SP_EXPANSION = "expansion";
    public static final String SP_URL = "url";
    public static final String SP_VERSION = "version";
    public static final String SP_REFERENCE = "reference";
    public static final String SP_ADDRESS = "address";
    public static final String SP_ADDDRESSCITY = "address-city";
    public static final String SP_ADDRESSCOUNTRY = "address-country";
    public static final String SP_ADDRESSSTATE = "address-state";
    public static final String SP_EMAIL = "email";
    public static final String SP_PATIENT = "patient";
    public static final String SP_PHONE = "phone";
    public static final String SP_PHONETIC = "phonetic";
    public static final String SP_TELECOM = "telecom";
    public static final String SP_TOKEN = "token";
    public static final String SP_ACTIVE = "active";
    public static final String SP_ORG = "organization";
    public static final String SP_ADDRESS_POSTALCODE = "address-postalcode";
    public static final String SP_ADDRESS_USE = "address-use";
    public static final String SP_ENDPOINT = "endpoint";
    public static final String SP_PARTOF = "partof";
    public static final String SP_TYPE = "type";

    public static final String SP_DEPENDSON = "dependson";
    public static final String SP_OTHER = "other";
    public static final String SP_PRODUCT = "product";
    public static final String SP_SOURCE_SYSTEM = "source-system";
    public static final String SP_TARGET = "target-system";

    public static final String SP_CATEGORY = "category";
    public static final String SP_CHARACTERISTIC = "characteristic";
    public static final String SP_PROGRAMNAME = "programname";
    public static final String SP_LOCALTION = "location";
    public static final String SP_COMMUNICATION = "communication";
    public static final String SP_ORGANIZATION = "organization";
    public static final String SP_PRACTITIONER = "practitioner";
    public static final String SP_SERVICE = "service";
    public static final String SP_ROLE = "role";
    public static final String SP_SPECIALTY = "specialty";
    public static final String SP_ACTOR = "actor";
    public static final String SP_APPOINTMENT_TYPE = "appointment-type";
    public static final String SP_INCOMINGREFERRAL = "incomingreferral";
    public static final String SP_PART_STATUS = "part-status";
    public static final String SP_SERVICE_TYPE = "service-type";
    public static final String SP_IDENTIFIER_SYSTEM = "identifier.system";
    public static final String SP_IDENTIFIER_VALUE = "identifier.value";

    public static final String SP_LAST_UPDATE = "_lastUpdated";
    public static final String SP_TAG = "_tag";
    public static final String SP_PROFILE = "_profile";
    public static final String SP_QUERY = "_query";
    public static final String SP_SECURITY = "_security";
    public static final String SP_CONTENT = "_content";
    public static final String SP_PAGE = "_getpage";

    public static final String SP_ABATEMENT_AGE = "abatement-age";
    public static final String SP_ABATEMENT_BOOLEAN = "abatement-boolean";
    public static final String SP_ABATEMENT_DATE = "abatement-date";
    public static final String SP_ABATEMENT_STRING = "abatement-string";
    public static final String SP_ASSERTED_DATE = "asserted-date";
    public static final String SP_ASSERTER = "asserter";
    public static final String SP_BODY_SITE = "body-site";
    public static final String SP_CLINICAL_STATUS = "clinical-status";
    public static final String SP_CONTEXT = "context";
    public static final String SP_ENCOUNTER = "encounter";
    public static final String SP_EVIDENCE = "evidence";
    public static final String SP_EVIDENCE_DETAIL = "evidence-detail";
    public static final String SP_ONSET_AGE = "onset-age";
    public static final String SP_ONSET_DATE = "onset-date";
    public static final String SP_ONSET_INFO = "onset-info";
    public static final String SP_SEVERITY = "severity";
    public static final String SP_STAGE = "stage";
    public static final String SP_SUBJECT = "subject";
    public static final String SP_VERIFICATION_STATUS = "verification-status";
    public static final String SP_BASED_ON = "based-on";
    public static final String SP_CARE_MANAGER = "care-manager";
    public static final String SP_CONDITION = "condition";
    public static final String SP_APPOINTMENT = "appointment";
    public static final String SP_PARTICIPANT = "participant";
    public static final String SP_DEFINITION = "definition";
    public static final String SP_PERFORMER = "performer";
    public static final String SP_ACTION = "action";
    public static final String SP_ASSESSOR = "assessor";
    public static final String SP_FINDING_CODE = "finding-code";
    public static final String SP_FINDING_REF = "finding-ref";
    public static final String SP_INVESTIGATION = "investigation";
    public static final String SP_PREVIOUS = "previous";
    public static final String SP_PROBLEM = "problem";
    public static final String SP_CONTAINER = "container";
    public static final String SP_FORM = "form";
    public static final String SP_INGREDIENT = "ingredient";
    public static final String SP_INGREDIENT_CODE = "ingredient-code";
    public static final String SP_MANUFACTURER = "manufacturer";
    public static final String SP_OVER_THE_COUNTER = "over-the-counter";
    public static final String SP_PACKAGE_ITEM = "package-item";
    public static final String SP_PACKAGE_ITEM_CODE = "package-item-code";
    public static final String SP_DEVICE = "device";
    public static final String SP_EFFECTIVE_TIME = "effective-time";
    public static final String SP_MEDICATION = "medication";
    public static final String SP_NOT_GIVEN = "not-given";
    public static final String SP_PRESCRIPTION = "prescription";
    public static final String SP_REASON_GIVEN = "reason-given";
    public static final String SP_REASON_NOT_GIVEN = "reason-not-given";
    public static final String SP_EFFECTIVE = "effective";
    public static final String SP_SOURCE = "source";
    public static final String SP_ACTIVITY_CODE = "activity-code";
    public static final String SP_ACTIVITY_DATE = "activity-date";
    public static final String SP_ACTIVITY_REFERENCE = "activity-reference";
    public static final String SP_CARE_TEAM = "care-team";
    public static final String SP_GOAL = "goal";
    public static final String SP_INTENT = "intent";
    public static final String SP_REPLACES = "replaces";
    public static final String SP_AUTHOR = "author";
    public static final String SP_IMPLICATED = "implicated";
    public static final String SP_START_DATE = "start-date";
    public static final String SP_TARGET_DATE = "target-date";
    public static final String SP_DOSE_SEQUENCE = "dose-sequence";
    public static final String SP_LOT_NUMBER = "lot-number";
    public static final String SP_NOTGIVEN = "notgiven";
    public static final String SP_REACTION = "reaction";
    public static final String SP_REACTION_DATE = "reaction-date";
    public static final String SP_REASON = "reason";
    public static final String SP_VACCINE_CODE = "vaccine-code";
    public static final String SP_CODE_VALUE_CONCEPT = "code-value-concept";
    public static final String SP_CODE_VALUE_DATE = "code-value-date";
    public static final String SP_CODE_VALUE_QUANTITY = "code-value-quantity";
    public static final String SP_CODE_VALUE_STRING = "code-value-string";
    public static final String SP_COMBO_CODE = "combo-code";
    public static final String SP_COMBO_CODE_VALUE_CONCEPT = "combo-code-value-concept";
    public static final String SP_COMBO_CODE_VALUE_QUANTITY = "combo-code-value-quantity";
    public static final String SP_COMBO_DATA_ABSENT_REASON = "combo-data-absent-reason";
    public static final String SP_COMBO_VALUE_CONCEPT = "combo-value-concept";
    public static final String SP_COMBO_VALUE_QUANTITY = "combo-value-quantity";
    public static final String SP_COMPONENT_CODE = "component-code";
    public static final String SP_COMPONENT_CODE_VALUE_CONCEPT = "component-code-value-concept";
    public static final String SP_COMPONENT_CODE_VALUE_QUANTITY = "component-code-value-quantity";
    public static final String SP_COMPONENT_DATA_ABSENT_REASON = "component-data-absent-reason";
    public static final String SP_COMPONENT_VALUE_CONCEPT = "component-value-concept";
    public static final String SP_COMPONENT_VALUE_QUANTITY = "component-value-quantity";
    public static final String SP_DATA_ABSENT_REASON = "data-absent-reason";
    public static final String SP_METHOD = "method";
    public static final String SP_RELATED = "related";
    public static final String SP_RELATED_TARGET = "related-target";
    public static final String SP_RELATED_TYPE = "related-type";
    public static final String SP_SPECIMEN = "specimen";
    public static final String SP_VALUE_CONCEPT = "value-concept";
    public static final String SP_VALUE_DATE="value-date";
    public static final String SP_VALUE_QUANTITY = "value-quantity";
    public static final String SP_VALUE_STRING = "value-string";
    public static final String SP_DIAGNOSIS = "diagnosis";
    public static final String SP_IMAGE = "image";
    public static final String SP_ISSUED = "issued";
    public static final String SP_RESULT = "result";
    public static final String SP_FHIR_ID = "fhirId";
    public static final String SP_SCHEDULE = "schedule";
    public static final String SP_SLOT_TYPE = "slot-type";
    public static final String SP_CLASS = "class";
    public static final String SP_LENGHTH = "length";
    public static final String SP_LOCATION_PERIOD= "location-period";
    public static final String SP_EPISODEOFCARE = "episodeofcare";
    public static final String SP_PARENT_CONCEPT_ID = "parentConceptId";
    public static final String SP_CODE_SYSTEM_ID = "codeSystemId";
    public static final String SP_CONCEPT_MAP_ID = "conceptMapID";
    public static final String SP_GROUP_ELEMENT_ID = "groupElementID";
    public static final String SP_ELEMENT_ENTITY_ID = "elementEntityID";
    public static final String SP_VALUE_SET_ID = "valueSetId";
    public static final String SP_VALUE_SET_COMPOSE_ID = "valueSetComposeId";
    public static final String SP_VALUE_SET_EXPANSION_ID = "valueSetExpansionId";
    public static final String SP_PARENT_CONTAIN_ID = "parentContainId";
    public static final String SP_PARTICIPANT_TYPE = "participant-type";
    public static final String SP_SERVICE_PROVIDER = "service-provider";
    public static final String SP_SPECIAL_ARRANGEMENT = "special-arrangement";
    public static final String SP_DESTINATION = "destination";
    public static final String SP_RECEIVER = "receiver";
    public static final String SP_RESPONSIBLEPARTY = "responsibleparty";
    public static final String SP_WHENHANDEDOVER = "whenhandedover";
    public static final String SP_WHENPREPARED = "whenprepared";
    public static final String SP_ANIMAL_BREED = "animal-breed";
    public static final String SP_ANIMAL_SPECIES = "animal-species";
    public static final String SP_DECEASED = "deceased";
    public static final String SP_GENERAL_PRACTITIONER = "general-practitioner";
    public static final String SP_LINK = "link";
    public static final String SP_DEATH_DATE = "death-date";
    public static final String SP_CODING = "coding";
    public static final String SP_DISPLAY_LANGUAGE = "displayLanguage";
    public static final String SP_PROPERTY = "property";
    public static final String SP_ISROOT = "isroot";
    public static final String SP_ROOT_CONCEPT = "rootconcept";
    public static final String SP_TABLE_NAME = "tableName";
    
    public static final String SP_OBSERVATION_MAX = "max";
    
    public static class PatientStaus {
        public static String getTen(int trangThai) {
            String[] tens = { "", "acitve", "remove" };
            return tens[trangThai];
        }

        public static final int REMOVE = 2;
        public static final int ACTIVE = 1;
    }

    public static final int VERSION_1 = 1;
    public static final String ENTITY_PROFILE_V1 = "https://fhir.emr.vncom/R4/StructureDefinition/";
    public static final String VALUESETCOMPOSE_TYPE_INCLUDE = "include";
    public static final String VALUESETCOMPOSE_TYPE_EXCLUDE = "exclude";
    public static final String NOT_PRESENT   = "not-present";
    public static final String EXAMPLE   = "example";
    public static final String FRAGMENT   = "fragment";
    public static final String COMPLETE   = "complete";
    public static final String FHIR_YTE360_URL   = "https://fhir.emr.com.vn/";
}
