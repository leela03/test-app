package com.datapine.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.datapine.domain.User;

/**
 * A aspect that writes to a log file.
 *
 */

@Aspect
@Component
public class LoggableAspect {

	static Logger LOGGER = LoggerFactory.getLogger(LoggableAspect.class);

	@Before("execution(* com.datapine.web.controller.LoginController.loginUser(..))")
	public void logAdvice(JoinPoint jp) {
		Object[] paramValues = jp.getArgs();
		if (jp.getArgs() != null && jp.getArgs().length > 0) {
			User user = (User) paramValues[0];
			LOGGER.debug("Login attempted with email: " + user.getEmail());
			System.out.println("Login attempted with email: " + user.getEmail());
		} else {
			LOGGER.debug("Login attempted");
			System.out.println("Login attempted");
		}
	}
	
}