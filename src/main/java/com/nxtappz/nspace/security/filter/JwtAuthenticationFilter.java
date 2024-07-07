package com.nxtappz.nspace.security.filter;




import com.nxtappz.nspace.security.exceptions.UnAuthorizedException;
import com.nxtappz.nspace.security.model.ErrorDetails;
import com.nxtappz.nspace.security.model.JwtAuthenticationToken;
import com.nxtappz.nspace.security.utils.AuthConstants;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public JwtAuthenticationFilter() {
		super("/api/**");
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		String authToken = request.getHeader(AuthConstants.HEADER);
		if(null == authToken ) {
			throw new UnAuthorizedException(HttpStatus.UNAUTHORIZED,"Missing authorisation token");
		}
		JwtAuthenticationToken token = new JwtAuthenticationToken(authToken, request.getMethod(),request.getRequestURI());
		return getAuthenticationManager().authenticate(token);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		//super.unsuccessfulAuthentication(request, response, failed);
		if(HttpMethod.OPTIONS.matches(request.getMethod())){
			response.setStatus(HttpStatus.OK.value());
		}else{
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getWriter().write(new ErrorDetails(request,failed).toJsonString());
		}
		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PATCH, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, authorization");
	}
}