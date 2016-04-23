package org.spaceapps.aircheck.server.controller.api;

import org.modelmapper.ModelMapper;
import org.spaceapps.aircheck.server.domain.Sample;
import org.spaceapps.aircheck.server.dto.SampleDto;
import org.spaceapps.aircheck.server.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = SampleController.REQUEST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class SampleController extends BaseApiController {

    public static final String REQUEST_URL = "/api/samples";

    private final SampleService sampleService;

    @Autowired
    public SampleController(ModelMapper modelMapper, SampleService sampleService) {
        super(modelMapper);
        this.sampleService = sampleService;
    }

    @Transactional(rollbackFor = Throwable.class, readOnly = true)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<SampleDto> getSamples(@RequestParam(value = "from", required = false) Date from,
                                      @RequestParam(value = "to", required = false) Date to) {
        List<Sample> samples = (from == null || to == null)
                ? sampleService.getSamples()
                : sampleService.getSamples(from, to);

        return convertTo(samples, SampleDto.class);
    }

    @Transactional(rollbackFor = Throwable.class)
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public SampleDto postSample(@RequestBody @Valid SampleDto sampleDto) {
        Sample sample = convertTo(sampleDto, Sample.class);
        sample = sampleService.addSample(sample);
        return convertTo(sample, SampleDto.class);
    }

}
