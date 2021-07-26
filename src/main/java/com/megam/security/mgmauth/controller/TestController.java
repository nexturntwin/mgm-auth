/*
 * Created on 26-Jul-2021
 * Created by @author murugan
 * Copyright � 2021 MEGAM [murugan]. All Rights Reserved.
 */
package com.megam.security.mgmauth.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.megam.security.mgmauth.domain.TestEntitiy;

/**
 * @author murugan
 *
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/test")
public class TestController {

	private List<TestEntitiy> someHeroes = List.of(new TestEntitiy(1, "Contact"), new TestEntitiy(2, "Employee"),
			new TestEntitiy(3, "Resource"));

	@GetMapping
	// @Secured("admin")
	@PreAuthorize("hasAuthority('admin')")
	public List<TestEntitiy> getAllEntities() {
		return someHeroes;
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority({'admin', 'developer', 'user'})")
	public TestEntitiy getSigleEntity(@PathVariable("id") String id) {
		return someHeroes.stream().filter(h -> Integer.toString(h.getId()).equals(id)).findFirst().orElse(null);
	}
}
