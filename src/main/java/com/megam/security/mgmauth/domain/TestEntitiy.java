/*
 * Created on 26-Jul-2021
 * Created by @author murugan
 * Copyright � 2021 MEGAM [murugan]. All Rights Reserved.
 */
package com.megam.security.mgmauth.domain;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author murugan
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestEntitiy {

	private int id;
	private String name;
	
}
