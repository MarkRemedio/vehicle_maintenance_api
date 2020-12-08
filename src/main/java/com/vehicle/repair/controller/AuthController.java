package com.vehicle.repair.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.repair.model.dto.LoginDto;
import com.vehicle.repair.model.dto.UserDto;
import com.vehicle.repair.model.form.LoginForm;
import com.vehicle.repair.model.form.RegisterForm;
import com.vehicle.repair.service.AuthService;

import lombok.RequiredArgsConstructor;
import io.swagger.annotations.ApiOperation;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/api/v01/auth"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

	private final AuthService userService;

	@ApiOperation(value = "Register User", notes = "username must be unique", response = UserDto.class)
	@PostMapping(path = "/register")
	public UserDto register(@Valid @RequestBody RegisterForm registerForm) {
		return userService.register(registerForm);
	}


	@ApiOperation(value = "Login User", notes = "creates jwt token", response = LoginDto.class)
	@PostMapping(path = "/login")
	public LoginDto login(@Valid  @RequestBody LoginForm loginForm) throws Exception{
		return userService.authenticate(loginForm);
	}
}
