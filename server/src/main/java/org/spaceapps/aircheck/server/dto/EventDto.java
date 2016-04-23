package org.spaceapps.aircheck.server.dto;

import org.spaceapps.aircheck.server.domain.EventType;

import java.util.List;

import static java.util.Collections.emptyList;

public class EventDto {

    private SampleDto sample;
    private List<EventType> types = emptyList();

    public EventDto() {
    }

    public SampleDto getSample() {
        return sample;
    }

    public void setSample(SampleDto sample) {
        this.sample = sample;
    }

    public List<EventType> getTypes() {
        return types;
    }

    public void setTypes(List<EventType> types) {
        this.types = types;
    }

}
