package com.datapine.dao;

import java.util.List;

import com.datapine.domain.Item;

public interface ItemDAO {

	Item save(Item item);
	
	Item update(Item item);

	void delete(Item item);

	Item findById(Long id);

	List<Item> findAll();
}
