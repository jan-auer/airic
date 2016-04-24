package org.spaceapps.aircheck.server.controller.api;

import org.modelmapper.ModelMapper;
import org.spaceapps.aircheck.server.domain.SymptomOccurrence;
import org.spaceapps.aircheck.server.dto.SymptomOccurrenceDto;
import org.spaceapps.aircheck.server.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = SymptomController.REQUEST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class SymptomController extends BaseApiController {

    public static final String REQUEST_URL = "/api/symptoms";

    private final AppService appService;

    @Autowired
    public SymptomController(ModelMapper modelMapper, AppService appService) {
        super(modelMapper);
        this.appService = appService;
    }

    @Transactional(rollbackFor = Throwable.class, readOnly = true)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<SymptomOccurrenceDto> getSymptoms(@RequestParam(value = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date from,
                                                  @RequestParam(value = "to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date to) {
        List<SymptomOccurrence> samples = (from == null || to == null)
                ? appService.getSymptoms()
                : appService.getSymptoms(from, to);

        return convertTo(samples, SymptomOccurrenceDto.class);
    }

    @Transactional(rollbackFor = Throwable.class)
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public SymptomOccurrenceDto postSample(@RequestBody @Valid SymptomOccurrenceDto symptomOccurrenceDto) {
        SymptomOccurrence occurrence = convertTo(symptomOccurrenceDto, SymptomOccurrence.class);
        occurrence = appService.recordSymptom(occurrence);
        return convertTo(occurrence, SymptomOccurrenceDto.class);
    }

}
