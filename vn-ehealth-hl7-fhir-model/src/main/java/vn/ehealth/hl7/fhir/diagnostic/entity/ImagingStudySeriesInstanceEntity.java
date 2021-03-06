package vn.ehealth.hl7.fhir.diagnostic.entity;

import org.hl7.fhir.r4.model.ImagingStudy.ImagingStudySeriesInstanceComponent;

import vn.ehealth.hl7.fhir.core.entity.BaseCoding;

public class ImagingStudySeriesInstanceEntity {
    public String uid;
    public int number;
    public BaseCoding sopClass;
    public String title;
    
    public static ImagingStudySeriesInstanceEntity fromImagingStudySeriesInstanceComponent(ImagingStudySeriesInstanceComponent obj) {
        if(obj == null) return null;
        
        var ent = new ImagingStudySeriesInstanceEntity();
        ent.uid = obj.getUid();
        ent.number = obj.getNumber();
        ent.sopClass = BaseCoding.fromCoding(obj.getSopClass());
        ent.title = obj.getTitle();
        
        return ent;
    }
    
    public static ImagingStudySeriesInstanceComponent toImagingStudySeriesInstanceComponent(ImagingStudySeriesInstanceEntity ent) {
        if(ent == null) return null;
        
        var obj = new ImagingStudySeriesInstanceComponent();
        obj.setUid(ent.uid);
        obj.setNumber(obj.getNumber());
        obj.setSopClass(BaseCoding.toCoding(ent.sopClass));
        obj.setTitle(ent.title);
        
        return obj;
    }
}
