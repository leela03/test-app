package com.datapine.service;

import java.util.List;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import com.datapine.domain.Item;
import com.datapine.exception.ResourceConflictException;
import com.datapine.exception.ResourceNotFoundException;

public interface ItemService {

	@PreAuthorize("hasRole('ROLE_USER')")
	Item save(Item item) throws ResourceConflictException;

	@PostFilter("hasPermission(filterObject, 'READ')")
	public List<Item> getAll();

	@PostFilter("hasPermission(filterObject, 'READ')")
	Item fetchById(Long id) throws ResourceNotFoundException;;

	void delete(Long id);

	Item update(Long id, Item item) throws ResourceNotFoundException;;
}
