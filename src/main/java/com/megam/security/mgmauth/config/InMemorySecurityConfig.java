/*
 * Created on 04-Jul-2021
 * Created by @author murugan
 * Copyright � 2021 MEGAM [murugan]. All Rights Reserved.
 */
package com.megam.security.mgmauth.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.megam.security.mgmauth.security.filters.RestHeaderAuthFilter;
import com.megam.security.mgmauth.security.filters.RestUrlAuthFilter;

/**
 * @author murugan
 *
 */
@Configuration
@EnableWebSecurity
@Import(BaseSecurityConfig.class)
@Order(2^30)
public class InMemorySecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	PasswordEncoder pswdEncoder;
	
	private RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {
		RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/user/**"));
		filter.setAuthenticationManager(authenticationManager);
		return filter;
	}

	private RestUrlAuthFilter restUrlAuthFilter(AuthenticationManager authenticationManager) {
		RestUrlAuthFilter filter = new RestUrlAuthFilter(new AntPathRequestMatcher("/user/**"));
		filter.setAuthenticationManager(authenticationManager);
		return filter;
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.headers().frameOptions().sameOrigin();
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/home").authenticated();
		http.authorizeRequests().antMatchers("/user/**").hasRole("ADMIN");
		http.addFilterBefore(restHeaderAuthFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(restUrlAuthFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
		http.formLogin();
		http.httpBasic();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		for (UserDetails userDetails : buildUserDetails()) {
			auth.inMemoryAuthentication().withUser(userDetails);
		}
		auth.inMemoryAuthentication().passwordEncoder(this.pswdEncoder).withUser("admin2")
				.password("{bcrypt}$2b$04$QVrPfhTyVkHDVxY36SaqIuNnGGAYujgCafwoIwuYzrIQsX4Fb3nVe").roles("ADMIN").and()
				.withUser("developer2")
				.password("{bcrypt2B5}$2b$05$bM/EW81Ol5FLtm8dmalYT.JM0j9La0f8afnzNJtTDmSjDIpfCyYsO").roles("DEVELOPER")
				.and().withUser("client2")
				.password("{bcrypt2B5}$2b$05$rGN0jTIUFifonBDkugelteoDs5p9a33nw5zJMDwgFWZl5aPd1zFpS").roles("CUSTOMER")
				.and().withUser("guest2")
				.password("{bcrypt2Y7}$2y$07$YuO/eizR6MTg/7Ltug30HeU2shMB6QdD5eXr9x63EZcCRixuyRWMu").roles("GUEST");
	}

	protected List<UserDetails> buildUserDetails() {
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		UserDetails admin = User.builder().passwordEncoder(pswdEncoder::encode).username("admin2").password("megam1")
				.roles("ADMIN").build();
		UserDetails developer = User.builder().passwordEncoder(pswdEncoder::encode).username("developer2")
				.password("megam2").roles("DEVELOPER", "ANALYST").build();
		UserDetails client = User.builder().passwordEncoder(pswdEncoder::encode).username("client2")
				.password("megam3").roles("CLIENT").build();
		UserDetails guest = User.builder().passwordEncoder(pswdEncoder::encode).username("guest2").password("megam4")
				.roles("GUEST").build();
		UserDetails test = User.builder().passwordEncoder(pswdEncoder::encode).username("test2").password("password")
				.roles("TEST").build();
		Collections.addAll(userDetails, admin, developer, client, guest, test);
		return userDetails;
	}
}
