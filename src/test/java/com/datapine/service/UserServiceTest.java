package com.datapine.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.datapine.dao.UserDAO;
import com.datapine.domain.User;
import com.datapine.exception.ResourceConflictException;
import com.datapine.exception.ResourceNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml", "classpath*:applicationPersistence.xml",
		"classpath*:applicationSecurity.xml", "classpath*:persistence.xml" })
@Configurable
public class UserServiceTest {

	@Mock
	UserDAO userDAO;

	@InjectMocks
	UserServiceImpl userService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldRegisterUser() throws Exception {
		final String email = "test@abc.com";
		final String password = "123";
		final User user = this.createUser(email, password);
		when(userDAO.findByEmail(email)).thenReturn(null);
		userService.register(user.getEmail(), user.getPassword());
		when(userDAO.findByEmail(email)).thenReturn(user);
		verify(userDAO).findByEmail(email);
		Assert.assertEquals(password, userDAO.findByEmail(email).getPassword());
	}
	
	@Test(expected=ResourceConflictException.class)
	public void shouldNotRegisterUserWithExistingEmail() throws Exception {
		final String email = "test@abc.com";
		final String password = "123";
		final User user = this.createUser(email, password);
		when(userDAO.findByEmail(email)).thenReturn(user);
		userService.register(user.getEmail(), user.getPassword());
		when(userDAO.findByEmail(email)).thenReturn(user);
		verify(userDAO).findByEmail(email);
	}

	@Test
	public void shouldUpdateUserPassword() throws Exception {
		final String email = "test@abc.com";
		final String password = "123";
		final String newPassword = "456";
		final long userId = 1;
		final User user = this.createUser(email, password);
		when(userDAO.findById(userId)).thenReturn(user);
		userService.updatePassword(userId, newPassword);
		user.setPassword(newPassword);
		when(userDAO.findById(userId)).thenReturn(user);
		verify(userDAO).findById(userId);
		Assert.assertEquals(newPassword, userDAO.findById(userId).getPassword());
	}
	
	@Test(expected=ResourceNotFoundException.class)
	public void shouldNotUpdatePasswordIfUserNotFound() throws Exception {
		final String newPassword = "456";
		final long userId = 1;
		when(userDAO.findById(userId)).thenReturn(null);
		userService.updatePassword(userId, newPassword);
		verify(userDAO).findById(userId);
	}
	
	@Test
	public void shouldFindUser() throws Exception {
		final String email = "test@abc.com";
		final String password = "123";
		final long userId = 1;
		final User user = this.createUser(email, password);
		when(userDAO.findById(userId)).thenReturn(user);
		userService.fetchById(userId);
		verify(userDAO).findById(userId);
		Assert.assertEquals(password, userDAO.findById(userId).getPassword());
	}
	
	@Test(expected=ResourceNotFoundException.class)
	public void shouldFindUserIfNotPresent() throws Exception {
		final long userId = 1;
		when(userDAO.findById(userId)).thenReturn(null);
		userService.fetchById(userId);
		verify(userDAO).findById(userId);
	}
	
	@Test
	public void shouldDeleteUser() throws Exception {
		final String email = "test@abc.com";
		final String password = "123";
		final long userId = 1;
		final User user = this.createUser(email, password);
		when(userDAO.findById(userId)).thenReturn(user);
		userService.delete(userId);
		when(userDAO.findById(userId)).thenReturn(null);
		verify(userDAO).findById(userId);
		Assert.assertNull(userDAO.findById(userId));
	}
	
	protected User createUser(final String email, final String password) {
		User user = new User(email);
		user.setPassword(password);
		return user;
	}
}
//public class UserServiceTest {
//	
//}
