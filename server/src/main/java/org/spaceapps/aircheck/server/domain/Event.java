package org.spaceapps.aircheck.server.domain;

import javax.persistence.*;
import java.util.List;

import static java.util.Collections.emptyList;

@Entity
@Table(name = "events")
public class Event {

    private long id;
    private Sample sample;
    private List<EventType> types = emptyList();

    public Event() {
    }

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "event_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    @ElementCollection(targetClass = EventType.class)
    @Enumerated(EnumType.STRING)
    public List<EventType> getTypes() {
        return types;
    }

    public void setTypes(List<EventType> types) {
        this.types = types;
    }

}
