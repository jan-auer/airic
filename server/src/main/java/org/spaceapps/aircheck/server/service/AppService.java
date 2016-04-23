package org.spaceapps.aircheck.server.service;

import org.spaceapps.aircheck.server.domain.Event;
import org.spaceapps.aircheck.server.domain.SymptomOccurrence;

public interface AppService {

    Event sendEvent(Event event);

    SymptomOccurrence recordSymptom(SymptomOccurrence occurrence);

}
