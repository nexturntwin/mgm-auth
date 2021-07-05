/*
 * Created on 04-Jul-2021
 * Created by murugan
 * Copyright � 2021 MEGAM [murugan425]. All Rights Reserved.
 */
package com.megam.security.mgmauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author murugan
 *
 */
@Controller
public class HomeController {
	
	@GetMapping({ "", "/" })
	public ResponseEntity<String> index() {
		return ResponseEntity.ok("INDEX PAGE!!!");
	}

	@GetMapping({ "home" })
	public ResponseEntity<String>  home() {
		return ResponseEntity.ok("HOME PAGE ACCESSIBLE.");
	}
	
	@GetMapping({ "ping" })
	public ResponseEntity<String> ping() {
		return ResponseEntity.ok("PONG! PONG!!!");
	}
	
	@GetMapping({ "dashboard" })
	public ResponseEntity<String>  dashboard() {
		return ResponseEntity.ok("DASHBOARD PAGE ACCESSIBLE.");
	}
	
	@GetMapping({ "user/add" })
	public ResponseEntity<String>  users() {
		return ResponseEntity.ok("USER MANAGEMENT PAGE ACCESSIBLE.");
	}
}
