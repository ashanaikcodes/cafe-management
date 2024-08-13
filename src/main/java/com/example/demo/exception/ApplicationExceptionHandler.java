package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler{
	
	  @ExceptionHandler(ApplicationException.class)
	    public ResponseEntity<Object> handleCustomException(ApplicationException ex) {
		  ErrorResponse response = new ErrorResponse(ex.getMessage(), ex.getDetails());
		  
		    return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    

}
