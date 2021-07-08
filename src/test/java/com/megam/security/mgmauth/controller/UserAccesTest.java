/*
 * Created on 08-Jul-2021
 * Created by @author murugan
 * Copyright � 2021 MEGAM [murugan]. All Rights Reserved.
 */
package com.megam.security.mgmauth.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author murugan
 *
 */
@SpringBootTest
@ActiveProfiles("test")
public class UserAccesTest extends BaseSecurityIT  {

	public static Stream<Arguments> getUsersWithDashboardAcces() {
		return Stream.of(Arguments.of("admin3", "megam1"), Arguments.of("developer3", "megam2"));
	}
	
	public static Stream<Arguments> getUsersWithApplicationAcces() {
		return Stream.of(Arguments.of("admin3", "megam1"), Arguments.of("developer3", "megam2"),
				Arguments.of("test3", "password"));
	}
	
	public static Stream<Arguments> getUsersWithProductAcces() {
		return Stream.of(Arguments.of("admin3", "megam1"), Arguments.of("developer3", "megam2"),
				Arguments.of("test3", "password"));
	}
	
	@ParameterizedTest
	@MethodSource("getUsersWithDashboardAcces")
	void testDashboardValidUsers(String username, String pswd) throws Exception {
		mockMvc.perform(get("/dashboard").with(httpBasic(username, pswd))).andExpect(status().is2xxSuccessful());
	}
	
	@ParameterizedTest
	@MethodSource("getUsersWithApplicationAcces")
	void testApplicationValidUsers(String username, String pswd) throws Exception {
		mockMvc.perform(get("/application").with(httpBasic(username, pswd))).andExpect(status().isOk());
	}
	
	@ParameterizedTest
	@MethodSource("getUsersWithProductAcces")
	void testProductValidUsers(String username, String pswd) throws Exception {
		mockMvc.perform(get("/application").with(httpBasic(username, pswd))).andExpect(status().isOk());
	}
	
	@Test
	void testDashboardAdminUser() throws Exception {
		mockMvc.perform(get("/dashboard").with(httpBasic("admin3", "megam1"))).andExpect(status().isOk());
	}
	
	@Test
	void testDashboardDevUser() throws Exception {
		mockMvc.perform(get("/dashboard").with(httpBasic("developer3", "megam2"))).andExpect(status().isOk());
	}
	
	@Test
	void testDashboardGuestUser() throws Exception {
		mockMvc.perform(get("/dashboard").with(httpBasic("guest3", "megam4"))).andExpect(status().isForbidden());
	}
	
	@Test
	void testProductAdminUser() throws Exception {
		mockMvc.perform(get("/product").with(httpBasic("admin3", "megam1"))).andExpect(status().is2xxSuccessful());
	}
	
	@Test
	void testProductGuestUser() throws Exception {
		mockMvc.perform(get("/product").with(httpBasic("guest3", "megam4"))).andExpect(status().isForbidden());
	}
	
	@Test
	void testPipelineDevUser() throws Exception {
		mockMvc.perform(get("/product").with(httpBasic("developer3", "megam2"))).andExpect(status().isOk());
	}
	
	@Test
	void testPipelineGuestUser() throws Exception {
		mockMvc.perform(get("/product").with(httpBasic("guest3", "megam4"))).andExpect(status().isForbidden());
	}
}
