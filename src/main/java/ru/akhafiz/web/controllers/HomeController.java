package ru.akhafiz.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p></p>
 *
 * @author akhafiz
 */
@Controller
public class HomeController {

    private static final String HOME_PAGE_VIEW_NAME = "home";

    @RequestMapping(value = {"/","/home"},method = RequestMethod.GET)
    public ModelAndView showHomePage() {

        ModelAndView mv = new ModelAndView(HOME_PAGE_VIEW_NAME);

        return mv;
    }
}
