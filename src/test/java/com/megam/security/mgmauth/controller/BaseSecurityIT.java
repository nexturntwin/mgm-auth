/*
 * Created on 04-Jul-2021
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.megam.security.mgmauth.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author murugan
 *
 */
public class BaseSecurityIT {

	@Autowired
	WebApplicationContext wac;

	MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
	}

}
