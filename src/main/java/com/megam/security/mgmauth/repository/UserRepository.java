/*
 * Created on 06-Jul-2021
 * Created by @author murugan
 * Copyright � 2021 MEGAM [murugan]. All Rights Reserved.
 */
package com.megam.security.mgmauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.megam.security.mgmauth.domain.User;

/**
 * @author murugan
 *
 */
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findUserByUsername(String username);
}
