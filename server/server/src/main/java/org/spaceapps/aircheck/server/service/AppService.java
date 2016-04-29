package org.spaceapps.aircheck.server.service;

import org.spaceapps.aircheck.server.domain.Event;
import org.spaceapps.aircheck.server.domain.SymptomOccurrence;

import java.util.Date;
import java.util.List;

public interface AppService {

    Event sendEvent(Event event);

    List<SymptomOccurrence> getSymptoms();

    List<SymptomOccurrence> getSymptoms(Date from, Date to);

    SymptomOccurrence recordSymptom(SymptomOccurrence occurrence);

}
