package com.nxtappz.nspace.security.services;


import com.nxtappz.nspace.security.model.JwtAuthenticationToken;
import com.nxtappz.nspace.security.model.JwtUser;
import com.nxtappz.nspace.security.model.JwtUserDetails;
import com.nxtappz.nspace.services.usermanagement.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	JwtService jwtService;

	@Autowired
	private AuthService authService;

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		
		JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
		String token = jwtAuthenticationToken.getToken();
		JwtUser jwtUser = jwtService.validate(token);
		
		if(null == jwtUser) {
			throw new RuntimeException("Invalid authentication token");
		}
		authService.validateRoles(jwtUser.getId(), jwtAuthenticationToken.getMethod(),jwtAuthenticationToken.getPath());
		List<GrantedAuthority> grants = AuthorityUtils.commaSeparatedStringToAuthorityList(jwtUser.getRole());
		 return  new JwtUserDetails(jwtUser.getId(), jwtUser.getUserName(),grants, token);
		
	}

}
