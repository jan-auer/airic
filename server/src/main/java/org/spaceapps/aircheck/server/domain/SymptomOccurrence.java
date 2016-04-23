package org.spaceapps.aircheck.server.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "symptom_occurrences")
public class SymptomOccurrence {

    private long id;
    private Symptom symptom;
    private Location location;
    private Date date;

    public SymptomOccurrence() {
    }

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "symptom_occurrence_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    public Symptom getSymptom() {
        return symptom;
    }

    public void setSymptom(Symptom symptom) {
        this.symptom = symptom;
    }

    public Location getLocation() {
        return location;
    }

    @Embedded
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
