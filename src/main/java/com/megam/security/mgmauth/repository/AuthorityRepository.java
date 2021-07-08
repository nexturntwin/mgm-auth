/*
 * Created on 06-Jul-2021
 * Created by @author murugan
 * Copyright � 2021 MEGAM [murugan]. All Rights Reserved.
 */
package com.megam.security.mgmauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.megam.security.mgmauth.domain.Authority;

/**
 * @author murugan
 *
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

}
