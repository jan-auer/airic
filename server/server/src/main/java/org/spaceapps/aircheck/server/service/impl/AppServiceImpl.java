package org.spaceapps.aircheck.server.service.impl;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import org.spaceapps.aircheck.server.domain.Event;
import org.spaceapps.aircheck.server.domain.SymptomOccurrence;
import org.spaceapps.aircheck.server.exception.NotificationException;
import org.spaceapps.aircheck.server.repository.EventRepository;
import org.spaceapps.aircheck.server.repository.SymptomOccurrenceRepository;
import org.spaceapps.aircheck.server.service.AppService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Service
public class AppServiceImpl implements AppService {

    private final Sender sender;
    private final EventRepository eventRepository;
    private final SymptomOccurrenceRepository symptomOccurrenceRepository;

    @Value("${org.spaceapps.aircheck.gcm_topic}")
    private String gcmTopic;
    @Value("${org.spaceapps.aircheck.gcm_retries}")
    private int gcmRetries;

    public AppServiceImpl(Sender sender, EventRepository eventRepository, SymptomOccurrenceRepository symptomOccurrenceRepository) {
        this.sender = sender;
        this.eventRepository = eventRepository;
        this.symptomOccurrenceRepository = symptomOccurrenceRepository;
    }

    @Override
    public Event sendEvent(Event event) {
        String types = event.getTypes().stream().map(Enum::name).collect(joining(","));
        Message message = new Message.Builder()
                .addData("types", types)
                .build();

        try {
            sender.send(message, gcmTopic, gcmRetries);
        } catch (IOException e) {
            String msg = String.format("Failed to push message to %s after %d retries", gcmTopic, gcmRetries);
            throw new NotificationException(msg, e);
        }

        return eventRepository.save(event);
    }

    @Override
    public List<SymptomOccurrence> getSymptoms() {
        return symptomOccurrenceRepository.findAll();
    }

    @Override
    public List<SymptomOccurrence> getSymptoms(Date from, Date to) {
        return symptomOccurrenceRepository.findByTimespan(from, to);
    }

    @Override
    public SymptomOccurrence recordSymptom(SymptomOccurrence occurrence) {
        return symptomOccurrenceRepository.save(occurrence);
    }

}
