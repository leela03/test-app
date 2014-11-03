package com.datapine.web.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.datapine.domain.Item;
import com.datapine.exception.ResourceConflictException;
import com.datapine.exception.ResourceNotFoundException;
import com.datapine.service.ItemService;

@Controller("itemController")
@RequestMapping("/items")
public class ItemController {
	
	static Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

	@Autowired
	private ItemService itemService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listItems() {
		ModelAndView model = new ModelAndView("items");
		model.addObject("items",itemService.getAll());
		return model;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Item findItem(@PathVariable final Long id, HttpServletResponse response) throws ResourceNotFoundException {
		return itemService.fetchById(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Item> createItem(@RequestBody Item item) throws ResourceConflictException {
		item = itemService.save(item);
		return new ResponseEntity<Item>(item, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteItem(@PathVariable final Long id) {
		itemService.delete(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Item updateItem(@PathVariable final Long id, @RequestBody Item item) throws ResourceNotFoundException {
		return itemService.update(id, item);
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
