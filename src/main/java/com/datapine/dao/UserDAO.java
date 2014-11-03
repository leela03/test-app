package com.datapine.dao;

import java.util.Iterator;
import java.util.List;

import com.datapine.domain.User;

public interface UserDAO {

	User save(User user);

	User update(User user);

	void delete(User user);

	User findById(Long id);

	User findByEmail(String email);

	Iterator<User> findAllOrderById();
	
	List<User> findAll();

}
