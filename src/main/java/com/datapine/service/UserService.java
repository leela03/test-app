package com.datapine.service;

import java.util.List;

import com.datapine.domain.User;
import com.datapine.exception.ResourceConflictException;
import com.datapine.exception.ResourceNotFoundException;
import com.datapine.exception.UnauthorizedException;

public interface UserService {

	User register(String email, String password) throws ResourceConflictException;

	User updatePassword(Long userId, String oldPassword, String newPassword) throws ResourceNotFoundException,
			UnauthorizedException;

	// add more methods here
	
	List<User> getAllUsers();
	
	boolean login(String email, String password) throws ResourceNotFoundException, UnauthorizedException;
	
	void delete(Long userId);
	
	User fetchById(Long userId) throws ResourceNotFoundException;

	User updatePassword(Long userId, String newPassword) throws ResourceNotFoundException;
}
