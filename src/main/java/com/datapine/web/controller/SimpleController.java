package com.datapine.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.datapine.domain.Item;
import com.datapine.domain.User;
import com.datapine.exception.ResourceConflictException;
import com.datapine.security.Encoder;
import com.datapine.service.ItemService;
import com.datapine.service.UserService;

@Controller
public class SimpleController {

	static Logger LOGGER = LoggerFactory.getLogger(SimpleController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private Encoder encoder;

	@RequestMapping(value = "/item", method = RequestMethod.POST)
	public String addItem(@ModelAttribute("item") Item item, BindingResult result, ModelMap model) {
		try {
			itemService.save(item);
		} catch (ResourceConflictException e) {
			e.printStackTrace();
		}
		return "items";
	}

	@RequestMapping(value = "/item", method = RequestMethod.GET)
	public String initItemForm(Model model) {
		model.addAttribute("item", new Item());
		return "addItem";
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ModelAndView listUsers() {
		ModelAndView model = new ModelAndView("users");
		model.addObject("users", userService.getAllUsers());
		return model;
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ModelAndView addUser(@ModelAttribute("user") User user, BindingResult result, ModelMap model)
			throws ResourceConflictException {
		userService.register(user.getEmail(), encoder.encode(user.getPassword()));
		ModelAndView models = new ModelAndView("users");
		models.addObject("users", userService.getAllUsers());
		return models;
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String initUserForm(Model model) {
		model.addAttribute("user", new User());
		return "addUser";
	}

	@ExceptionHandler(ResourceConflictException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public @ResponseBody void handleResourceNotFoundException(ResourceConflictException ex) {
		LOGGER.warn("Trying to add a resource that already exists", ex.getMessage());
	}
}
