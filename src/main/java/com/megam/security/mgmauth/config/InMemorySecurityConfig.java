/*
 * Created on 04-Jul-2021
 * Created by @author murugan
 * Copyright � 2021 MEGAM [murugan]. All Rights Reserved.
 */
package com.megam.security.mgmauth.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.megam.security.mgmauth.cipher.CipherFactories;

/**
 * @author murugan
 *
 */
@Configuration
@EnableWebSecurity
@Order(Ordered.HIGHEST_PRECEDENCE)
public class InMemorySecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${megam.security.salt}")
	private String salt;

	@Bean
	PasswordEncoder pswdEncoder() {
		return CipherFactories.createDelegatingPasswordEncoder(salt);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin")
				.password("{bcrypt}$2b$04$QVrPfhTyVkHDVxY36SaqIuNnGGAYujgCafwoIwuYzrIQsX4Fb3nVe").roles("ADMIN").and()
				.withUser("developer")
				.password("{bcrypt2B5}$2b$05$bM/EW81Ol5FLtm8dmalYT.JM0j9La0f8afnzNJtTDmSjDIpfCyYsO").roles("DEVELOPER")
				.and().withUser("client")
				.password("{bcrypt2B5}$2b$05$rGN0jTIUFifonBDkugelteoDs5p9a33nw5zJMDwgFWZl5aPd1zFpS").roles("CUSTOMER")
				.and().withUser("guest")
				.password("{bcrypt2Y7}$2y$07$YuO/eizR6MTg/7Ltug30HeU2shMB6QdD5eXr9x63EZcCRixuyRWMu").roles("GUEST");
	}

	protected List<UserDetails> buildUserDetails() {
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		UserDetails admin = User.builder().passwordEncoder(pswdEncoder()::encode).username("admin")
				.password("{bcrypt}$2b$04$QVrPfhTyVkHDVxY36SaqIuNnGGAYujgCafwoIwuYzrIQsX4Fb3nVe").roles("ADMIN")
				.build();
		UserDetails developer = User.builder().passwordEncoder(pswdEncoder()::encode).username("developer")
				.password("{bcrypt2B5}$2b$05$bM/EW81Ol5FLtm8dmalYT.JM0j9La0f8afnzNJtTDmSjDIpfCyYsO")
				.roles("DEVELOPER", "ANALYST").build();
		UserDetails client = User.builder().passwordEncoder(pswdEncoder()::encode).username("client")
				.password("{bcrypt2B5}$2b$05$rGN0jTIUFifonBDkugelteoDs5p9a33nw5zJMDwgFWZl5aPd1zFpS").roles("CLIENT")
				.build();
		UserDetails guest = User.builder().passwordEncoder(pswdEncoder()::encode).username("guest")
				.password("{bcrypt2Y7}$2y$07$YuO/eizR6MTg/7Ltug30HeU2shMB6QdD5eXr9x63EZcCRixuyRWMu").roles("GUEST")
				.build();
		Collections.addAll(userDetails, admin, developer, client, guest);
		return userDetails;
	}
}
