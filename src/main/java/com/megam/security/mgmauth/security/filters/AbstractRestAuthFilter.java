/*
 * Created on 05-Jul-2021
 * Created by @author murugan
 * Copyright � 2021 MEGAM [murugan]. All Rights Reserved.
 */
package com.megam.security.mgmauth.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import lombok.extern.slf4j.Slf4j;

/**
 * @author murugan
 *
 */
@Slf4j
public abstract class AbstractRestAuthFilter extends AbstractAuthenticationProcessingFilter {

	/**
	 * @param requiresAuthenticationRequestMatcher
	 */
	public AbstractRestAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		String userId = getKey(request);
		String password = getSecret(request);

		if (null == userId)
			userId = "";
		if (null == password)
			password = "";
		log.debug("MEGAM: RestAuthFilter - Authenticating for userId: " + userId);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userId, password);
		if (ObjectUtils.isEmpty(userId)) {
			return null;
		} else {
			return this.getAuthenticationManager().authenticate(token);
		}
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		log.debug("Request to process authentication by MEGAM-AUTH");

		try {
			Authentication authResult = attemptAuthentication(request, response);
			if (null == authResult) {
				chain.doFilter(request, response);
			} else {
				successfulAuthentication(request, response, chain, authResult);
			}
		} catch (AuthenticationException failed) {
			log.error("MEGAM-AUTH: RestAuthFilter - Authenticating request failed.");
			unsuccessfulAuthentication(request, response, failed);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		log.debug("MEGAM-AUTH: Authentication success. Updating SecurityContextHolder to contain: " + authResult);

		SecurityContextHolder.getContext().setAuthentication(authResult);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		SecurityContextHolder.clearContext();

		log.debug("MEGAM-AUTH: Authentication request failed: " + failed.toString(), failed);
		log.debug("Updated SecurityContextHolder to contain null Authentication");

		log.debug("No failure URL set, sending 401 Unauthorized error");
		response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
	}
	
	protected abstract String getKey(HttpServletRequest request);

	protected abstract String getSecret(HttpServletRequest request);

}
