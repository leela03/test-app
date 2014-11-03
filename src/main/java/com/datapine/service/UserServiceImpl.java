package com.datapine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.datapine.dao.UserDAO;
import com.datapine.domain.User;
import com.datapine.exception.ResourceConflictException;
import com.datapine.exception.ResourceNotFoundException;
import com.datapine.exception.UnauthorizedException;

@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	private UserDAO userDAO;

	@Transactional
	public User register(final String email, String password) throws ResourceConflictException {
		User user = userDAO.findByEmail(email);
		if (user != null) {
			throw new ResourceConflictException("User with this email already registered");
		}
		user = new User(email);
		user.setPassword(password);
		userDAO.save(user);
		return user;
	}

	@Override
	@Transactional
	public User updatePassword(Long userId, String oldPassword, String newPassword) throws ResourceNotFoundException,
			UnauthorizedException {
		User user = userDAO.findById(userId);
		if (user == null) {
			throw new ResourceNotFoundException("User Not found");
		}
		if (!user.getPassword().equals(oldPassword)) {
			throw new UnauthorizedException("Invalid credentials");
		}
		user.setPassword(newPassword);
		userDAO.update(user);
		return user;
	}

	@Override
	public List<User> getAllUsers() {
		return userDAO.findAll();
	}

	@Override
	public boolean login(String email, String password) throws ResourceNotFoundException, UnauthorizedException {
		User user = userDAO.findByEmail(email);
		if (user == null) {
			throw new ResourceNotFoundException("User Not found");
		}
		if (!password.equals(user.getPassword())) {
			throw new UnauthorizedException("Invalid credentials");
		}
		return true;
	}

	@Override
	@Transactional
	public void delete(Long userId) {
		User user = userDAO.findById(userId);
		if (user != null) {
			userDAO.delete(user);
		}
	}

	@Override
	public User fetchById(Long userId) throws ResourceNotFoundException {
		User user = userDAO.findById(userId);
		if (user == null) {
			throw new ResourceNotFoundException("User Not found");
		}
		return user;
	}

	@Override
	public User updatePassword(Long userId, String newPassword) throws ResourceNotFoundException {
		User user = userDAO.findById(userId);
		if (user == null) {
			throw new ResourceNotFoundException("User Not found");
		}
		user.setPassword(newPassword);
		return userDAO.update(user);
	}

}
