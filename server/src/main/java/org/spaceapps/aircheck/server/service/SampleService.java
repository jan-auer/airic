package org.spaceapps.aircheck.server.service;

import org.spaceapps.aircheck.server.domain.Sample;

import java.util.Date;
import java.util.List;

public interface SampleService {

    List<Sample> getSamples();

    List<Sample> getSamples(Date from, Date to);

    Sample addSample(Sample sample);

    List<Sample> addSamples(Iterable<Sample> samples);

}
