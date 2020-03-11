package vn.ehealth.hl7.fhir.diagnostic.dao.transform;

import org.apache.commons.collections4.Transformer;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.springframework.stereotype.Component;

import vn.ehealth.hl7.fhir.core.util.DataConvertUtil;
import vn.ehealth.hl7.fhir.diagnostic.entity.DiagnosticReportEntity;

@Component
public class DiagnosticReportEntityToFHIRDiagnosticReport
        implements Transformer<DiagnosticReportEntity, DiagnosticReport> {
    @Override
    public DiagnosticReport transform(DiagnosticReportEntity ent) {
        var obj = DiagnosticReportEntity.toDiagnosticReport(ent);
        obj.setMeta(DataConvertUtil.getMeta(ent, "DiagnosticReport-v1.0"));
        obj.setExtension(ent.extension);
        obj.setId(ent.fhir_id);
        return obj;
    }
}
