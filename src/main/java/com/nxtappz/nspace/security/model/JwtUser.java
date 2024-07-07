package com.nxtappz.nspace.security.model;

import lombok.Data;

@Data
public class JwtUser {

	private String role;
	private Long id;
	private String userName;

}
