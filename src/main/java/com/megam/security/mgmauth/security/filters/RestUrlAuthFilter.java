/*
 * Created on 05-Jul-2021
 * Created by @author murugan
 * Copyright � 2021 MEGAM [murugan]. All Rights Reserved.
 */
package com.megam.security.mgmauth.security.filters;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @author murugan
 *
 */
public class RestUrlAuthFilter extends AbstractRestAuthFilter {

	/**
	 * @param requiresAuthenticationRequestMatcher
	 */
	public RestUrlAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	/**
	 * @param request
	 * @return
	 */
	@Override
	protected String getKey(HttpServletRequest request) {
		return request.getParameter("Api-Key");
	}

	/**
	 * @param request
	 * @return
	 */
	@Override
	protected String getSecret(HttpServletRequest request) {
		return request.getParameter("Api-Secret");
	}
}
