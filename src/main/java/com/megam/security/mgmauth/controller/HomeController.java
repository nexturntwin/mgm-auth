/*
 * Created on 04-Jul-2021
 * Created by murugan
 * Copyright � 2021 MEGAM [murugan425]. All Rights Reserved.
 */
package com.megam.security.mgmauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
	
	@Secured("ROLE_ADMIN")
	@GetMapping({ "dashboard" })
	public ResponseEntity<String>  dashboard() {
		return ResponseEntity.ok("DASHBOARD PAGE ACCESSIBLE.");
	}

	@Secured({"ROLE_ADMIN", "ROLE_DEVELOPER", "ROLE_TEST"})
	@GetMapping({ "application" })
	public ResponseEntity<String>  application() {
		return ResponseEntity.ok("APPLICATION PAGE ACCESSIBLE.");
	}
	
	@GetMapping({ "product" })
	public ResponseEntity<String>  product() {
		return ResponseEntity.ok("PRODUCT PAGE ACCESSIBLE.");
	}
	
	@GetMapping({ "pipeline" })
	public ResponseEntity<String>  pipeline() {
		return ResponseEntity.ok("PIPELINE CONFIGURATION ACCESSIBLE.");
	}
	
	@GetMapping({ "report" })
	public ResponseEntity<String>  report() {
		return ResponseEntity.ok("REPORTS PAGE ACCESSIBLE.");
	}
	
	@GetMapping({ "user/manage" })
	public ResponseEntity<String>  users() {
		return ResponseEntity.ok("USER MANAGEMENT PAGE ACCESSIBLE.");
	}
}
