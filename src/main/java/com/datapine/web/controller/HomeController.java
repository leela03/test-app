package com.datapine.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	/*
	 * This controller will only work, if the Spring MVC settings are set
	 * completely and correctly (just as a hint).
	 */

	@RequestMapping("/")
	public ModelAndView showWelcome() {
		return new ModelAndView("index");
	}

}
