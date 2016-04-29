package org.spaceapps.aircheck.server.controller.api;

import org.modelmapper.ModelMapper;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public abstract class BaseApiController {

    private final ModelMapper modelMapper;

    public BaseApiController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    protected <To, From> List<To> convertTo(List<From> objects, Class<To> toClass) {
        return objects == null ? emptyList() : objects.stream().map(e -> convertTo(e, toClass)).collect(toList());
    }

    protected <To, From> To convertTo(From object, Class<To> toClass) {
        return modelMapper.map(object, toClass);
    }

    protected ModelMapper getModelMapper() {
        return modelMapper;
    }

}
