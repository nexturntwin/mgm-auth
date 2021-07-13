/*
 * Created on 08-Jul-2021
 * Created by @author murugan
 * Copyright � 2021 MEGAM [murugan]. All Rights Reserved.
 */
package com.megam.security.mgmauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.megam.security.mgmauth.security.filters.RestHeaderAuthFilter;
import com.megam.security.mgmauth.security.filters.RestUrlAuthFilter;
import com.megam.security.mgmauth.services.UserDetailsAuthService;

/**
 * @author murugan
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Import(BaseSecurityConfig.class)
@Order(2^25)
public class JpaSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PasswordEncoder pswdEncoder;
	
	@Autowired
	private UserDetailsAuthService userDetailsAuthService;
	
	private RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {
		RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/dashboard"));
		filter.setAuthenticationManager(authenticationManager);
		return filter;
	}

	private RestUrlAuthFilter restUrlAuthFilter(AuthenticationManager authenticationManager) {
		RestUrlAuthFilter filter = new RestUrlAuthFilter(new AntPathRequestMatcher("/dashboard"));
		filter.setAuthenticationManager(authenticationManager);
		return filter;
	}
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.headers().frameOptions().sameOrigin();
		//http.csrf().disable();
		http.csrf().ignoringAntMatchers("/h2-console/**");
		http.formLogin();
		http.httpBasic();
		http.authorizeRequests().antMatchers("/home").authenticated();
		/*
		http.authorizeRequests().antMatchers("dashboard").hasAnyRole("ADMIN", "DEVELOPER");
		http.authorizeRequests().antMatchers("product", "application").hasAnyRole("ADMIN", "DEVELOPER", "ANALYST", "TEST");
		http.authorizeRequests().antMatchers("pipeline").hasAnyRole("DEVELOPER", "TEST");
		http.authorizeRequests().antMatchers("users/**").hasRole("ADMIN");
		http.authorizeRequests().antMatchers("report").hasAnyRole("ANALYST", "CUSTOMER");
		*/
		http.addFilterBefore(restHeaderAuthFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(restUrlAuthFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsAuthService).passwordEncoder(pswdEncoder);
	}
	
	
}
