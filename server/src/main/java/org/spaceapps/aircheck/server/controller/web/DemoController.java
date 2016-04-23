package org.spaceapps.aircheck.server.controller.web;

import org.spaceapps.aircheck.server.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/demo")
public class DemoController {

    private final AppService appService;

    @Autowired
    public DemoController(AppService appService) {
        this.appService = appService;
    }

    @Transactional(rollbackFor = Throwable.class, readOnly = true)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index(ModelMap model) {
        return new ModelAndView("demo", model);
    }

    @Transactional(rollbackFor = Throwable.class, readOnly = true)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ModelAndView push(ModelMap model) {
        return new ModelAndView("demo", model);
    }

}
