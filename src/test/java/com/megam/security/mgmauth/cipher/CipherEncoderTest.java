/*
 * Created on 04-Jul-2021
 * Created by @author murugan
 * Copyright � 2021 MEGAM [murugan]. All Rights Reserved.
 */
package com.megam.security.mgmauth.cipher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;

/**
 * @author murugan
 *
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@WebMvcTest(CipherEncoder.class)
@TestPropertySource(properties = "megham.security.salt=testkey")
public class CipherEncoderTest {

	@Value("${megham.security.salt}")
	private String salt;

	@Autowired
	public CipherEncoder cipherEncoder;

	@BeforeEach
	void setUp(TestInfo testInfo) {
	  log.info(String.format("%s - test started", testInfo.getDisplayName()));
	}
	
	@AfterEach
	void tearDown(TestInfo testInfo) {
	  log.info(String.format("%s - test finished", testInfo.getDisplayName()));
	}
	
	// Note: Logged the hash value multiple times for validation purpose.

	@Test
	public void logMd5EncodersHashValues() {
		log.info("Salt Value used: " + salt);
		log.info(cipherEncoder.md5Hashing("murugan"));
		log.info(cipherEncoder.md5Hashing("murugan"));
		log.info(cipherEncoder.md5HashingWithSalt("murugan"));
		log.info(cipherEncoder.md5HashingWithSalt("murugan"));
		log.info(cipherEncoder.md5HashingWithSalt("murugan", salt));
		log.info(cipherEncoder.md5HashingWithSalt("murugan", salt));
		assertTrue(true);
	}

	@Test
	public void testMd5Encoders() {
		assertEquals(cipherEncoder.md5Hashing("murugan"), cipherEncoder.md5Hashing("murugan"),
				"md5Hashing - encoder test failed");
		assertEquals(cipherEncoder.md5HashingWithSalt("murugan"), cipherEncoder.md5HashingWithSalt("murugan"),
				"md5Hashing - default Salt test failed");
		assertEquals(cipherEncoder.md5HashingWithSalt("murugan", salt),
				cipherEncoder.md5HashingWithSalt("murugan", salt), "md5Hashing - custom Salt test failed");
	}

	@Test
	public void logNoOpEncoderValues() {
		log.info(cipherEncoder.noOpEncoder("murugan"));
		log.info(cipherEncoder.noOpEncoder("murugan"));
		assertTrue(true);
	}

	@Test
	public void logLdapEncoderHashValues() {
		log.info(cipherEncoder.ldapHashing("testpswd"));
		log.info(cipherEncoder.ldapHashing("testpswd"));
		String encodedTxt = cipherEncoder.ldapHashing("murugan");
		assertTrue(cipherEncoder.ldapPasswordValid("murugan", encodedTxt));
	}

	@Test
	public void logSha256EncoderHashValues() {
		log.info(cipherEncoder.sha256Hashing("testpswd"));
		log.info(cipherEncoder.sha256Hashing("testpswd"));
		String encodedTxt = cipherEncoder.sha256Hashing("murugan");
		assertTrue(cipherEncoder.sha256PasswordValid("murugan", encodedTxt));
	}

	@Test
	public void logBcryptEncoderHashValues() {
		log.info(cipherEncoder.bCryptHashing("testpswd"));
		log.info(cipherEncoder.bCryptHashing("testpswd"));
		String encodedTxt = cipherEncoder.bCryptHashing("murugan");
		assertTrue(cipherEncoder.bCryptPasswordValid("murugan", encodedTxt));
	}
}
