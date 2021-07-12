/*
 * Created on 06-Jul-2021
 * Created by @author murugan
 * Copyright � 2021 MEGAM [murugan]. All Rights Reserved.
 */
package com.megam.security.mgmauth.bootstrap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.megam.security.mgmauth.domain.Authority;
import com.megam.security.mgmauth.domain.RoleEntity;
import com.megam.security.mgmauth.domain.UserEntity;
import com.megam.security.mgmauth.repository.AuthorityRepository;
import com.megam.security.mgmauth.repository.RoleRepository;
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
	private final RoleRepository roleRepository;

	@Autowired
	private final AuthorityRepository authorityRepository;

	@Override
	public void run(String... args) throws Exception {
		loadAuthorizedUsers();
	}

	private void loadAuthorizedUsers() {

		log.info(this.getClass().getSimpleName() + ": loadAuthorities()");
		Authority dashboardView = authorityRepository.save(Authority.builder().permission("dashboard.view").build());
		Authority productView = authorityRepository.save(Authority.builder().permission("product.view").build());
		Authority applicationManage = authorityRepository
				.save(Authority.builder().permission("application.manage").build());
		Authority applicationView = authorityRepository
				.save(Authority.builder().permission("application.view").build());
		Authority pipelineManage = authorityRepository.save(Authority.builder().permission("pipeline.manage").build());
		Authority pipelineView = authorityRepository.save(Authority.builder().permission("pipeline.view").build());
		Authority reportsView = authorityRepository.save(Authority.builder().permission("reports.view").build());
		Authority usersManage = authorityRepository.save(Authority.builder().permission("users.manage").build());
		Authority usersView = authorityRepository.save(Authority.builder().permission("users.view").build());

		log.info(this.getClass().getSimpleName() + ": loadRoles()");
		RoleEntity adminRole = RoleEntity.builder().role("ADMIN").build();
		RoleEntity developerRole = RoleEntity.builder().role("DEVELOPER").build();
		RoleEntity analystRole = RoleEntity.builder().role("ANALYST").build();
		RoleEntity customerRole = RoleEntity.builder().role("CUSTOMER").build();
		RoleEntity guestRole = RoleEntity.builder().role("GUEST").build();
		RoleEntity testRole = RoleEntity.builder().role("TEST").build();

		adminRole.setAuthorities(
				Set.of(dashboardView, productView, applicationManage, pipelineManage, reportsView, usersManage));
		developerRole.setAuthorities(Set.of(dashboardView, productView, applicationManage, pipelineManage));
		analystRole.setAuthorities(Set.of(dashboardView, productView, applicationManage, reportsView, usersView));
		customerRole.setAuthorities(Set.of(applicationView, pipelineView, reportsView));
		guestRole.setAuthorities(Set.of(productView, applicationView));
		testRole.setAuthorities(Set.of(reportsView));
		roleRepository.saveAll(Arrays.asList(adminRole, developerRole, analystRole, customerRole, guestRole, testRole));

		log.info(this.getClass().getSimpleName() + ": loadUsers()");
		List<UserEntity> users = new ArrayList<UserEntity>();
		UserEntity admin = UserEntity.builder().username("admin3").password(pswdEncoder.encode("megam1"))
				.role(adminRole).build();
		UserEntity developer = UserEntity.builder().username("developer3").password(pswdEncoder.encode("megam2"))
				.role(developerRole).role(analystRole).build();
		UserEntity client = UserEntity.builder().username("client3").password(pswdEncoder.encode("megam3"))
				.role(customerRole).build();
		UserEntity guest = UserEntity.builder().username("guest3").password(pswdEncoder.encode("megam4"))
				.role(guestRole).build();
		UserEntity test = UserEntity.builder().username("test3").password(pswdEncoder.encode("password")).role(testRole)
				.build();
		Collections.addAll(users, admin, developer, client, guest, test);
		//users.stream().map(user -> userRepository.save(user));
		userRepository.saveAll(users);
		log.info("Total Users Loaded : {}", userRepository.count());
	}
}
