/*
 * Created on 06-Jul-2021
 * Created by @author murugan
 * Copyright � 2021 MEGAM [murugan]. All Rights Reserved.
 */
package com.megam.security.mgmauth.bootstrap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.megam.security.mgmauth.domain.Authority;
import com.megam.security.mgmauth.domain.User;
import com.megam.security.mgmauth.repository.AuthorityRepository;
import com.megam.security.mgmauth.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author murugan
 *
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserBootstrap implements CommandLineRunner {

	@Autowired
	PasswordEncoder pswdEncoder;

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private final AuthorityRepository authorityRepository;

	@Override
	public void run(String... args) throws Exception {
		loadAuthorizedUsers();
	}

	private void loadAuthorizedUsers() {
		log.info(this.getClass().getSimpleName() + ": loadAuthorities()");
		Authority adminRole = authorityRepository.save(Authority.builder().role("ADMIN").build());
		Authority developerRole = authorityRepository.save(Authority.builder().role("DEVELOPER").build());
		Authority customerRole = authorityRepository.save(Authority.builder().role("CUSTOMER").build());
		Authority guestRole = authorityRepository.save(Authority.builder().role("GUEST").build());
		Authority analystRole = authorityRepository.save(Authority.builder().role("ANALYST").build());
		Authority testRole = authorityRepository.save(Authority.builder().role("TEST").build());

		log.info(this.getClass().getSimpleName() + ": loadUsers()");
		List<User> users = new ArrayList<User>();

		User admin = User.builder().username("admin3").password(pswdEncoder.encode("megam1")).authority(adminRole)
				.build();
		User developer = User.builder().username("developer3").password(pswdEncoder.encode("megam2"))
				.authorities(Set.of(developerRole, analystRole)).build();
		User client = User.builder().username("client3").password(pswdEncoder.encode("megam3")).authority(customerRole)
				.build();
		User guest = User.builder().username("guest3").password(pswdEncoder.encode("megam4")).authority(guestRole)
				.build();
		User test = User.builder().username("test3").password(pswdEncoder.encode("password")).authority(testRole)
				.build();
		Collections.addAll(users, admin, developer, client, guest, test);
		userRepository.saveAll(users);
		log.info("Total Users Loaded : {}", userRepository.count());

	}
}
