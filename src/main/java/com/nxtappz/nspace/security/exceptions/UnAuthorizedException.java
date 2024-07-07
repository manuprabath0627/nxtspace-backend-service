package com.nxtappz.nspace.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends AuthenticationException {

	private static final long serialVersionUID = -5261954641307007397L;
	private HttpStatus httpStatus;
	
	public UnAuthorizedException(HttpStatus httpStatus, String message) {
		super(message); 
		this.httpStatus = httpStatus;
	}
	public UnAuthorizedException(String message) {
		super(message);
		this.httpStatus = HttpStatus.UNAUTHORIZED;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	

}
