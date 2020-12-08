package com.vehicle.repair.util;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.vehicle.repair.persistence.entity.User;
import com.vehicle.repair.persistence.repository.UserRepository;

import lombok.AllArgsConstructor;


@Component
@AllArgsConstructor
public class AuthenticationUtil implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "username not found");
		}

		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),new ArrayList<>());
	}

	public User getAuthenticatedUser() {
		return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
	}
}
