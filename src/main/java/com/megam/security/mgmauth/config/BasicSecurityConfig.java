/*
 * Created on 04-Jul-2021
 * Created by murugan
 * Copyright � 2021 MEGAM [murugan425]. All Rights Reserved.
 */
package com.megam.security.mgmauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.megam.security.mgmauth.cipher.CipherFactories;

/**
 * @author murugan
 *
 */
@Configuration
@EnableWebSecurity
@Order(Ordered.LOWEST_PRECEDENCE)
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${megam.security.salt}")
	private String salt;
	
	@Bean("pswdEncoderBasic")
	PasswordEncoder pswdEncoder() {
		return CipherFactories.createDelegatingPasswordEncoder(salt);
	}
	
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
		UserDetails admin = User.builder().passwordEncoder(pswdEncoder()::encode).username("admin").password("megam1").roles("ADMIN")
				.build();
		UserDetails developer = User.builder().passwordEncoder(pswdEncoder()::encode).username("developer").password("megam2")
				.roles("DEVELOPER", "ANALYST").build();
		UserDetails client = User.builder().passwordEncoder(pswdEncoder()::encode).username("client").password("megam3").roles("CLIENT")
				.build();
		UserDetails guest = User.builder().passwordEncoder(pswdEncoder()::encode).username("guest").password("megam4").roles("GUEST")
				.build();
		UserDetails test = User.builder().passwordEncoder(pswdEncoder()::encode).username("test").password("password").roles("TEST")
				.build();
		return new InMemoryUserDetailsManager(admin, developer, client, guest);
	}
}
