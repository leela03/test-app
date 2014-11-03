package com.datapine.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.datapine.domain.User;
import com.datapine.exception.ResourceNotFoundException;
import com.datapine.exception.UnauthorizedException;
import com.datapine.security.Encoder;
import com.datapine.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private Encoder encoder;

	@RequestMapping(method = RequestMethod.GET)
	public String initLogin(Model model) {
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			return "success";
		}
		model.addAttribute("user", new User());
		return "login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String loginUser(@ModelAttribute("user") User user, BindingResult result, ModelMap model) {
		try {
			String password = encoder.encode(user.getPassword());
			if (userService.login(user.getEmail(), password)) {
				Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), password);
				SecurityContextHolder.getContext().setAuthentication(auth);
				return "success";
			}
			return "loginFail";
		} catch (ResourceNotFoundException re) {
			// TODO Auto-generated catch block
			return "loginFail";
		} catch (UnauthorizedException ue) {
			// TODO Auto-generated catch block
			return "loginFail";
		}
	}
}
