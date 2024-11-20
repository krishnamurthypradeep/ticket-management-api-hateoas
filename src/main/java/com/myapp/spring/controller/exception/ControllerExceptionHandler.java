package com.myapp.spring.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.myapp.spring.exception.InvalidTicketStateException;
import com.myapp.spring.exception.MissingDescriptionException;
import com.myapp.spring.exception.MissingResolutionSummaryException;
import com.myapp.spring.exception.TicketNotFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {

	public ControllerExceptionHandler() {
		// TODO Auto-generated constructor stub
	}
	
	@ExceptionHandler(InvalidTicketStateException.class)
	public ResponseEntity<String> handleInvalidTicketState(InvalidTicketStateException ex){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(TicketNotFoundException.class)
	public ResponseEntity<String> handleTicketNotFoundException(TicketNotFoundException ex){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MissingDescriptionException.class)
	public ResponseEntity<String> handleMissingDescription(MissingDescriptionException ex){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MissingResolutionSummaryException.class)
	public ResponseEntity<String> handleMissingResolutionSummary(MissingResolutionSummaryException ex){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	}
}
