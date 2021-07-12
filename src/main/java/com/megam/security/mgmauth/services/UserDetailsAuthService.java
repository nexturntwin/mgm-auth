/*
 * Created on 08-Jul-2021
 * Created by @author murugan
 * Copyright � 2021 MEGAM [murugan]. All Rights Reserved.
 */
package com.megam.security.mgmauth.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.megam.security.mgmauth.domain.Authority;
import com.megam.security.mgmauth.domain.UserEntity;
import com.megam.security.mgmauth.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author murugan
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsAuthService implements UserDetailsService {

	@Autowired
	private final UserRepository userRepository;

	/**
	 * Convert the custom User Authority to Spring Authority class object
	 * 
	 * @param Set<Authority> authorities
	 * @return Set<SimpleGrantedAuthority> granterAuthorities
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findUserByUsername(username).orElseThrow(() -> {
			log.debug("Username: " + username + " not found in Megam Inbuilt Authorization Repository");
			return new UsernameNotFoundException(
					"Username: " + username + " not found in Megam Inbuilt Authorization Repository");
		});
		log.debug("Loading User details from Megam Auth Repository for username: " + username + ". Megam User Details: "
				+ user.toString());

		UserDetails userDetails = User.builder().username(user.getUsername()).password(user.getPassword())
				.accountExpired(!user.isAccountNonExpired()).accountLocked(!user.isAccountNonLocked())
				.credentialsExpired(!user.isCredentialsNonExpired())
				.authorities(getGrantedAuthorities(user.getAuthorities())).build();
		log.debug("User Details: " + userDetails.toString());
		return userDetails;
	}

	/**
	 * Convert the custom User Authority to Spring Authority class object
	 * 
	 * @param Set<Authority> authorities
	 * @return Set<SimpleGrantedAuthority> granterAuthorities
	 */
	private Collection<? extends GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		if (!authorities.isEmpty()) {
			grantedAuthorities.addAll(authorities.stream().map(Authority::getPermission).map(SimpleGrantedAuthority::new)
					.collect(Collectors.toSet()));
		}
		return grantedAuthorities;
	}

}
