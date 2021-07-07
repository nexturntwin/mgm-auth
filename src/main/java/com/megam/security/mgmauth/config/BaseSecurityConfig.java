/*
 * Created on 06-Jul-2021
 * Created by @author murugan
 * Copyright � 2021 MEGAM [murugan]. All Rights Reserved.
 */
package com.megam.security.mgmauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.megam.security.mgmauth.cipher.CipherFactories;

/**
 * @author murugan
 *
 */
@Configuration
public class BaseSecurityConfig {

	@Value("${megam.security.salt}")
	private String salt;
	
	@Bean
	PasswordEncoder pswdEncoder() {
		return CipherFactories.createDelegatingPasswordEncoder(salt);
	}
	
}
