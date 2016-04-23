package org.spaceapps.aircheck.server.controller.web;

import org.spaceapps.aircheck.server.domain.Event;
import org.spaceapps.aircheck.server.domain.EventType;
import org.spaceapps.aircheck.server.exception.NotificationException;
import org.spaceapps.aircheck.server.service.AppService;
import org.spaceapps.aircheck.server.util.WebMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

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
    public ModelAndView push(ModelMap model, RedirectAttributes attributes) {
        try {
            Event event = new Event();
            event.setTypes(Arrays.asList(EventType.HIGH_CO, EventType.HIGH_CO2));
            appService.sendEvent(event);

            attributes.addFlashAttribute(WebMessage.INFO, "Notification sent.");
        } catch (NotificationException e) {
            attributes.addFlashAttribute(WebMessage.ERROR, "Could not send the notification!");
        }

        return new ModelAndView("redirect:/demo", model);
    }

}
