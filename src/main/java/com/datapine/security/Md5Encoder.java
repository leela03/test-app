package com.datapine.security;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Component;


/**
 * Writing a simple MD5 encoder
 * TODO: A stronger encoding library could be used
 *
 */
@Component("encoder")
public class Md5Encoder implements Encoder {

	private static final Md5PasswordEncoder PASSWORD_ENCODER = new Md5PasswordEncoder();

	public  String encode(final String password) {
		return PASSWORD_ENCODER.encodePassword(password, null);
	}

}
