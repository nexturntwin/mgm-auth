/*
 * Created on 04-Jul-2021
 * Created by murugan
 * Copyright � 2021 MEGAM [murugan425]. All Rights Reserved.
 */
package com.megam.security.mgmauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	
	@PreAuthorize("hasAuthority('dashboard.view')")
	@GetMapping({ "dashboard" })
	public ResponseEntity<String>  dashboard() {
		return ResponseEntity.ok("DASHBOARD PAGE ACCESSIBLE.");
	}

	@PreAuthorize("hasAnyAuthority({'application.manage', 'application.view'})")
	@GetMapping({ "application" })
	public ResponseEntity<String>  application() {
		return ResponseEntity.ok("APPLICATION PAGE ACCESSIBLE.");
	}
	
	@PreAuthorize("hasAuthority('product.view')")
	@GetMapping({ "product" })
	public ResponseEntity<String>  product() {
		return ResponseEntity.ok("PRODUCT PAGE ACCESSIBLE.");
	}
	
	@PreAuthorize("hasAnyAuthority({'pipeline.manage','pipeline.view'})")
	@GetMapping({ "pipeline" })
	public ResponseEntity<String>  pipeline() {
		return ResponseEntity.ok("PIPELINE CONFIGURATION ACCESSIBLE.");
	}
	
	@PreAuthorize("hasAuthority('reports.view')")
	@GetMapping({ "report" })
	public ResponseEntity<String>  report() {
		return ResponseEntity.ok("REPORTS PAGE ACCESSIBLE.");
	}
	
	@PreAuthorize("hasAuthority('users.manage')")
	@GetMapping({ "users/manage" })
	public ResponseEntity<String>  users() {
		return ResponseEntity.ok("USER MANAGEMENT PAGE ACCESSIBLE.");
	}
}
