package com.example.demo.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {
	
	private String message;
	private String details;

	
	 public ErrorResponse(
		      String message, String details) {
		    this.message = message;
		    this.details = details;
		  }
	
}
