package com.datapine.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.datapine.dao.UserDAO;


/**
 * A custom authentication provider that looks up the database for 
 * authenticating a user
 *
 */
@Component(value = "userAuthenticationProvider")
public class UserAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private Encoder encoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String name = authentication.getName();
		String password = encoder.encode(authentication.getCredentials().toString());
		com.datapine.domain.User user = userDAO.findByEmail(authentication.getName());
		if (user != null && user.getPassword().equals(password)) {
			List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
			grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
			Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
			return auth;

		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
