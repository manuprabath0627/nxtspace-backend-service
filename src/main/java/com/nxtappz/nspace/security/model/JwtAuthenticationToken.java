package com.nxtappz.nspace.security.model;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private final String path;
	private final String method;
	private String token;
	
	public JwtAuthenticationToken(String token,String method, String path) {
		super(null,null);
		this.token = token;
		this.path = path;
		this.method = method;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPath() {
		return path;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return null;
	}

	public String getMethod() {
		return method;
	}
}
