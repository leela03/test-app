package com.datapine.exception;

public class BaseException extends Exception {
	private static final long serialVersionUID = -6145498047078216719L;

	public static final String NEWLINE = System.getProperty("line.separator");

	private String message;

	public BaseException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(message).append(NEWLINE);
		if (this.getStackTrace() != null) {
			sb.append("StackTrace:").append(NEWLINE);
			for (final StackTraceElement element : this.getStackTrace()) {
				sb.append(element.toString()).append(NEWLINE);
			}
		}
		return sb.toString();
	}

}
