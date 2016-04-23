package org.spaceapps.aircheck.server.dto;

import org.spaceapps.aircheck.server.domain.Location;
import org.spaceapps.aircheck.server.domain.Symptom;

import java.util.Date;

public class SymptomOccurrenceDto {

    private Symptom symptom;
    private Location location;
    private Date date;

    public SymptomOccurrenceDto() {
    }

    public Symptom getSymptom() {
        return symptom;
    }

    public void setSymptom(Symptom symptom) {
        this.symptom = symptom;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
