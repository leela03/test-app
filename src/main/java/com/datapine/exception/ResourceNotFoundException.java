package com.datapine.exception;

public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 2993826237674370118L;
	
	public ResourceNotFoundException(String string) {
		super(string);
	}

}