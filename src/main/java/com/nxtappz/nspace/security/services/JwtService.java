package com.nxtappz.nspace.security.services;

import com.nxtappz.nspace.security.exceptions.UnAuthorizedException;
import com.nxtappz.nspace.security.model.JwtUser;
import com.nxtappz.nspace.security.model.JwtUserDetails;
import com.nxtappz.nspace.services.usermanagement.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

	public static final String JWT_USER_NAME = "userName";
	public final static String JWT_USER_ID = "userID";
	public final static String JWT_USER_ROLE = "role";

	@Value("${nspace.securiry.token}")
	private String key;


	public JwtUser validate(String token) {

		token = token.replace("Bearer ","");

		JwtUser jwtUser = null;
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(key )
					.parseClaimsJws(token)
					.getBody();
			jwtUser = new JwtUser();
			jwtUser.setUserName(claims.getSubject());
			jwtUser.setId(((Number)claims.get(JWT_USER_ID)).longValue());
			jwtUser.setRole((String)claims.get(JWT_USER_ROLE));
			jwtUser.setUserName((String)claims.get(JWT_USER_NAME));


		} catch (ExpiredJwtException e) {
			throw new UnAuthorizedException(HttpStatus.UNAUTHORIZED, "Token Expired");
		} catch (UnsupportedJwtException e) {
			throw new UnAuthorizedException(HttpStatus.UNAUTHORIZED, "Unsuported JWT");
		} catch (MalformedJwtException e) {
			throw new UnAuthorizedException(HttpStatus.UNAUTHORIZED, "Invalid Token");
		} catch (SignatureException e) {
			throw new UnAuthorizedException(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
		} catch (NumberFormatException e) {
			throw new UnAuthorizedException(HttpStatus.UNAUTHORIZED, "Invalid Token");
		} catch (IllegalArgumentException e) {
			throw new UnAuthorizedException(HttpStatus.UNAUTHORIZED, "Invalid Token");
		}
		return jwtUser;
		
	}

	public String getNewToken(Long id, String username, String userRole) {

		Claims claims = Jwts.claims();
		claims.put(JWT_USER_ID, id);
		claims.put(JWT_USER_ROLE,userRole);
		claims.put(JWT_USER_NAME, username);


		return Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS512,key)
				.compact();
	}

	public Long getCurrentUserId(){
		JwtUserDetails user = getRequestedUser();
		if(null == user){
			throw new UnAuthorizedException("Unauthorized");
		}
		try {
			return user.getId();
		} catch (Exception e) {
			throw new UnAuthorizedException("Unauthorized");
		}
	}

	public JwtUserDetails getRequestedUser(){
		return (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
	}
}
