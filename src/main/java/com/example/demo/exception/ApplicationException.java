package com.example.demo.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApplicationException extends RuntimeException {

	private String message;
	private String details;

	
	 public ApplicationException(
		      String message, String details) {
		    this.message = message;
		    this.details = details;
		  }
	
}
