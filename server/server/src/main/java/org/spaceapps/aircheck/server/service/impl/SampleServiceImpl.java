package org.spaceapps.aircheck.server.service.impl;

import org.spaceapps.aircheck.server.domain.Sample;
import org.spaceapps.aircheck.server.repository.SampleRepository;
import org.spaceapps.aircheck.server.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SampleServiceImpl implements SampleService {

    private final SampleRepository sampleRepository;

    @Autowired
    public SampleServiceImpl(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    @Override
    public List<Sample> getSamples() {
        return sampleRepository.findAll();
    }

    @Override
    public List<Sample> getSamples(Date from, Date to) {
        return sampleRepository.findByTimespan(from, to);
    }

    @Override
    public Sample addSample(Sample sample) {
        return sampleRepository.save(sample);
    }

    @Override
    public List<Sample> addSamples(Iterable<Sample> samples) {
        return sampleRepository.save(samples);
    }

}
