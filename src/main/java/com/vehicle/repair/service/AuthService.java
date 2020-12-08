package com.vehicle.repair.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.vehicle.repair.model.dto.LoginDto;
import com.vehicle.repair.model.dto.UserDto;
import com.vehicle.repair.model.form.LoginForm;
import com.vehicle.repair.model.form.RegisterForm;
import com.vehicle.repair.persistence.entity.User;
import com.vehicle.repair.persistence.repository.UserRepository;
import com.vehicle.repair.util.AuthenticationUtil;
import com.vehicle.repair.util.EntityDtoMapperUtil;
import com.vehicle.repair.util.JwtTokenUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenUtil jwtTokenUtil;
	private final AuthenticationManager authManager;
	private final AuthenticationUtil authenticationUtil;
	private final EntityDtoMapperUtil modelMapper;


	public LoginDto authenticate(LoginForm loginForm) throws Exception {
		authenticate(loginForm.getUsername(), loginForm.getPassword());
		final UserDetails userDetails = authenticationUtil.loadUserByUsername(loginForm.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);

		LoginDto loginDto = modelMapper.map(userRepository.findByUsername(loginForm.getUsername()), LoginDto.class);
		loginDto.setToken(token);
		loginDto.setTokenExpiration(jwtTokenUtil.getExpirationDateFromToken(token));

		return loginDto;
	}

	public UserDto register(RegisterForm form){

		if(userRepository.findByUsername(form.getUsername()) != null) {
			log.debug("duplicate username : " + form.getUsername());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username must be unique");
		}

		User user = new User();
		user.setUsername(form.getUsername());
		user.setPassword(passwordEncoder.encode(form.getPassword()));
		user.setFirstname(form.getFirstname());
		user.setMiddlename(form.getMiddlename());
		user.setLastname(form.getLastname());

		return modelMapper.map(userRepository.save(user), UserDto.class);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
