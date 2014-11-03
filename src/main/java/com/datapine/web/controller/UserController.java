package com.datapine.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.datapine.domain.User;
import com.datapine.exception.ResourceConflictException;
import com.datapine.exception.ResourceNotFoundException;
import com.datapine.security.Encoder;
import com.datapine.service.UserService;

@Controller("userController")
@RequestMapping("/admin/users")
public class UserController {

	static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private Encoder encoder;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public List<User> fetchUsers() {
		return userService.getAllUsers();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public User fetchUser(@PathVariable final Long id, HttpServletResponse response) throws ResourceNotFoundException {
		return userService.fetchById(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<User> createUser(@RequestBody User user) throws ResourceConflictException {
		user = userService.register(user.getEmail(), encoder.encode(user.getPassword()));
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteUser(@PathVariable final Long id) {
		userService.delete(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public User updateUser(@PathVariable final Long id, @RequestBody User user) throws ResourceNotFoundException {
		return userService.updatePassword(id, encoder.encode(user.getPassword()));
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody void handleResourceNotFoundException(ResourceNotFoundException ex) {
		LOGGER.warn("A resource that doesn't exist was requested", ex.getMessage());
	}

	@ExceptionHandler(ResourceConflictException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public @ResponseBody void handleResourceNotFoundException(ResourceConflictException ex) {
		LOGGER.warn("Trying to add a resource that already exists", ex.getMessage());
	}

}
