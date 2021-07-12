/*
 * Created on 12-Jul-2021
 * Created by @author murugan
 * Copyright � 2021 MEGAM [murugan]. All Rights Reserved.
 */
package com.megam.security.mgmauth.security.authority;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author murugan
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('users.manage')")
public @interface UsersManageAuthority {

}
