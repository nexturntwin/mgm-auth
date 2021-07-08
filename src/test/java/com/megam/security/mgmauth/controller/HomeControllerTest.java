/*
 * Created on 04-Jul-2021
 * Created by @author murugan
 * Copyright � 2021 MEGAM [murugan]. All Rights Reserved.
 */
package com.megam.security.mgmauth.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author murugan
 *
 */
@SpringBootTest
@ActiveProfiles("test")
public class HomeControllerTest extends BaseSecurityIT {

	// ping - Bypass Authentication.
	// home - Valid Authentication required.

	@Test
	void testIndexPage() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk());
	}

	@Test
	void testHomeWithInvalidUser() throws Exception {
		mockMvc.perform(get("/home").with(httpBasic("invalidserid", "blahblah")))
				.andExpect(status().is4xxClientError());
	}

	@WithMockUser("testuser")
	@Test
	void testHomeWithValidUser() throws Exception {
		mockMvc.perform(get("/home")).andExpect(status().isOk());
	}

	@WithMockUser("mockedUserQwer")
	@Test
	void testHomeWithMockedUser() throws Exception {
		mockMvc.perform(get("/home")).andExpect(status().isOk());
	}

	// Providing no user for test method fails as spring security is configured.
	@Test
	void testHomeNoUser() throws Exception {
		mockMvc.perform(get("/home")).andExpect(status().is4xxClientError());
	}

	@Test
	void testPingNoUser() throws Exception {
		mockMvc.perform(get("/ping")).andExpect(status().isOk());
	}

	@Test
	void testPingWithValidUser() throws Exception {
		mockMvc.perform(get("/ping").with(httpBasic("test2", "password"))).andExpect(status().isOk());
	}

	@Test
	void testPingWithInvalidUser() throws Exception {
		mockMvc.perform(get("/ping").with(httpBasic("invaliduser", "blabla"))).andExpect(status().is4xxClientError());
	}

	@Test
	void testHomeWithAdminUser() throws Exception {
		mockMvc.perform(get("/home").with(httpBasic("admin2", "megam1"))).andExpect(status().isOk());
	}

	@Test
	void testHomeWithDevUser() throws Exception {
		mockMvc.perform(get("/home").with(httpBasic("developer2", "megam2"))).andExpect(status().isOk());
	}

	@Test
	void testHomeWithClientUser() throws Exception {
		mockMvc.perform(get("/home").with(httpBasic("client2", "megam3"))).andExpect(status().isOk());
	}

	@Test
	void testHomeWithGuestUser() throws Exception {
		mockMvc.perform(get("/home").with(httpBasic("guest2", "megam4"))).andExpect(status().isOk());
	}

	@Test
	void testAddUserWithAdminUser() throws Exception {
		mockMvc.perform(get("/user/manage").with(httpBasic("admin2", "megam1"))).andExpect(status().isOk());
	}

	@Test
	void testAddUserWithGuestUser() throws Exception {
		mockMvc.perform(get("/user/manage").with(httpBasic("guest1", "megam4"))).andExpect(status().is4xxClientError());
	}

	@Test
	void testManageUserWithHeaderFilterGuestUser() throws Exception {
		mockMvc.perform(get("/user/manage").header("Api-Key", "guest2").header("Api-Secret", "megam4"))
				.andExpect(status().isOk());
	}
	
	@Test
	void testManageUserWithUrlParamFilterDevUser() throws Exception {
		mockMvc.perform(get("/user/manage").param("Api-Key", "developer2").param("Api-Secret", "megam2"))
				.andExpect(status().isOk());
	}
}
