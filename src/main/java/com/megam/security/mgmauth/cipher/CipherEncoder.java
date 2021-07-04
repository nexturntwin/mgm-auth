/*
 * Created on 04-Jul-2021
 * Created by @author murugan
 * Copyright � 2021 MEGAM [murugan]. All Rights Reserved.
 */
package com.megam.security.mgmauth.cipher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * @author murugan
 * 
 * MegamAuth will be using bCrypt as the default encoder.
 * Note: Deprecated PasswordEncoders provided for legacy and testing purposes only.
 */
@Component("cipherEncoder")
public class CipherEncoder {

	@Value("${megham.security.salt}")
	private String salt;

	// NoOp - Not recommended, doesn't do any manipulation on the given text.
	public String noOpEncoder(String text) {
		PasswordEncoder noOpEncoder = NoOpPasswordEncoder.getInstance();
		return noOpEncoder.encode(text);
	}

	public String ldapHashing(String text) {
		PasswordEncoder ldapHashing = new LdapShaPasswordEncoder();
		return ldapHashing.encode(text);
	}

	public Boolean ldapPasswordValid(String rawPassword, String encodedPassword) {
		PasswordEncoder ldapHashing = new LdapShaPasswordEncoder();
		return ldapHashing.matches(rawPassword, encodedPassword);
	}

	public String sha256Hashing(String text) {
		PasswordEncoder sha256Hashing = new StandardPasswordEncoder(salt);
		return sha256Hashing.encode(text);
	}

	public Boolean sha256PasswordValid(String rawPassword, String encodedPassword) {
		PasswordEncoder sha256Hashing = new StandardPasswordEncoder(salt);
		return sha256Hashing.matches(rawPassword, encodedPassword);
	}

	public String md5Hashing(String text) {
		return DigestUtils.md5DigestAsHex(text.getBytes());
	}

	public String md5HashingWithSalt(String rawPassword) {
		return DigestUtils.md5DigestAsHex((rawPassword + salt).getBytes());
	}

	public String md5HashingWithSalt(String rawPassword, String salt) {
		return DigestUtils.md5DigestAsHex((rawPassword + salt).getBytes());
	}

	public String bCryptHashing(String rawPassword) {
		PasswordEncoder bcryptHashing = new BCryptPasswordEncoder(BCryptVersion.$2B, 4);
		return bcryptHashing.encode(rawPassword);
	}
	
	public Boolean bCryptPasswordValid(String rawPassword, String encodedPassword) {
		PasswordEncoder bcryptHashing = new BCryptPasswordEncoder(BCryptVersion.$2B, 4);
		return bcryptHashing.matches(rawPassword, encodedPassword);
	}
	
	public String bCryptHashing(String rawPassword, BCryptVersion bcVersion, int strength) {
		PasswordEncoder bcryptHashing = new BCryptPasswordEncoder(bcVersion, strength);
		return bcryptHashing.encode(rawPassword);
	}

	public Boolean bCryptPasswordValid(String rawPassword, String encodedPassword, BCryptVersion bcVersion, int strength) {
		PasswordEncoder bcryptHashing = new BCryptPasswordEncoder(bcVersion, strength);
		return bcryptHashing.matches(rawPassword, encodedPassword);
	}
}
