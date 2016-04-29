package org.spaceapps.aircheck.server.controller.web;

import org.spaceapps.aircheck.server.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class DashboardController {

    private final SampleService sampleService;

    @Autowired
    public DashboardController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @Transactional(rollbackFor = Throwable.class, readOnly = true)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index(ModelMap model) {
        return new ModelAndView("dashboard", model);
    }

}
