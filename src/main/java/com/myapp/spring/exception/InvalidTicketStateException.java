package com.myapp.spring.exception;

public class InvalidTicketStateException extends RuntimeException {

	public InvalidTicketStateException() {
		// TODO Auto-generated constructor stub
	}

	public InvalidTicketStateException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidTicketStateException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidTicketStateException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidTicketStateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
