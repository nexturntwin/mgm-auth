/*
 * Created on 04-Jul-2021
 * Created by @author murugan
 * Copyright � 2021 MEGAM [murugan]. All Rights Reserved.
 */
package com.megam.security.mgmauth.cipher;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

/**
 * @author murugan
 *
 */
public class CipherFactories {

	public static PasswordEncoder createDelegatingPasswordEncoder(String saltKey) {
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put("bcrypt", new BCryptPasswordEncoder(BCryptVersion.$2B, 4));
		encoders.put("bcrypt2B5", new BCryptPasswordEncoder(BCryptVersion.$2B, 5));
		encoders.put("bcrypt2Y7", new BCryptPasswordEncoder(BCryptVersion.$2Y, 7));
		encoders.put("bcrypt2A9", new BCryptPasswordEncoder(BCryptVersion.$2A, 9));
		encoders.put("pbkdf2", new Pbkdf2PasswordEncoder(saltKey));
		encoders.put("scrypt", new SCryptPasswordEncoder());
		encoders.put("argon2", new Argon2PasswordEncoder());
		return new DelegatingPasswordEncoder("bcrypt", encoders);
	}

	private CipherFactories() {
	}
}
