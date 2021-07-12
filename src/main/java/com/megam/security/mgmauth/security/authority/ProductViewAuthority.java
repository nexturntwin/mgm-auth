/*
 * Created on 12-Jul-2021
 * Created by @author murugan
 * Copyright � 2021 MEGAM [murugan]. All Rights Reserved.
 */
package com.megam.security.mgmauth.security.authority;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author murugan
 *
 */
@PreAuthorize("hasAuthority('product.view')")
public @interface ProductViewAuthority {

}
