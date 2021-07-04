/*
 * Created on 04-Jul-2021
 * Created by murugan
 * Copyright � 2021 MEGAM [murugan425]. All Rights Reserved.
 */
package com.megam.security.mgmauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

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
		http.csrf().ignoringAntMatchers("/h2-console/**");
		http.authorizeRequests(authorize -> {
			authorize.antMatchers("/", "/ping", "/h2-console/**").permitAll();
		}).authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
	}

	// Instead of using the single user & password from the yml file, configuring
	// multiple inmemory users
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails admin = User.withDefaultPasswordEncoder().username("admin").password("admin").roles("ADMIN")
				.build();
		UserDetails developer = User.withDefaultPasswordEncoder().username("developer").password("password")
				.roles("DEVELOPER", "ANALYST").build();
		UserDetails client = User.withDefaultPasswordEncoder().username("client").password("password").roles("CLIENT")
				.build();
		UserDetails guest = User.withDefaultPasswordEncoder().username("guest").password("password").roles("GUEST")
				.build();
		return new InMemoryUserDetailsManager(admin, developer, client, guest);
	}
}
