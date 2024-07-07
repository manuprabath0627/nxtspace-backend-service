package com.nxtappz.nspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class NspaceApplication {

	public static final String API_V1 = "api/v1/";
	public static final String API_AUTH = "authentication/";

	public static void main(String[] args) {
		SpringApplication.run(NspaceApplication.class, args);
	}

}
