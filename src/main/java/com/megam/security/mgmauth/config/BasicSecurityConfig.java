/*
 * Created on 04-Jul-2021
 * Created by murugan
 * Copyright � 2021 MEGAM [murugan425]. All Rights Reserved.
 */
package com.megam.security.mgmauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;



/**
 * @author murugan
 *
 */
@Configuration
@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.headers().frameOptions().sameOrigin();
		
		http.authorizeRequests(authorize -> {
			authorize.antMatchers("/", "/ping" , "/h2-console/**").permitAll();
		}).authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
	}
	
}
