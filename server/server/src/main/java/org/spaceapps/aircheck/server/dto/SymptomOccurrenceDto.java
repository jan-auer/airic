package org.spaceapps.aircheck.server.dto;

import org.spaceapps.aircheck.server.domain.Symptom;

import java.util.Date;

public class SymptomOccurrenceDto {

    private Symptom symptom;
    private LocationDto location;
    private Date date;

    public SymptomOccurrenceDto() {
    }

    public Symptom getSymptom() {
        return symptom;
    }

    public void setSymptom(Symptom symptom) {
        this.symptom = symptom;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
