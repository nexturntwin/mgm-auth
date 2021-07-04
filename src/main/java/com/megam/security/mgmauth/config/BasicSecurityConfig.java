/*
 * Created on 04-Jul-2021
 * Created by murugan
 * Copyright � 2021 MEGAM [murugan425]. All Rights Reserved.
 */
package com.megam.security.mgmauth.config;

/**
 * @author murugan
 *
 */
//@Configuration
//@EnableWebSecurity
public class BasicSecurityConfig {
	
	/*
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.headers().frameOptions().sameOrigin();
		
		http.authorizeRequests(authorize -> {
			authorize.antMatchers("/", "/ping" , "/h2-console/**").permitAll();
		}).authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
	}
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails adminUser = User.builder().username("murugan").password("qwert").
				roles("ADMIN").build();
		UserDetails customerUser = User.builder().username("test").password("asdfg")
				.roles("READONLY").build();
		return new InMemoryUserDetailsManager(adminUser, customerUser);
	}
	*/
}
