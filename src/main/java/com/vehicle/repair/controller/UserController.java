package com.vehicle.repair.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.repair.model.dto.UserDto;
import com.vehicle.repair.service.UserService;

import lombok.RequiredArgsConstructor;
import io.swagger.annotations.ApiOperation;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/api/v01/user"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	private final UserService userService;

	@ApiOperation(value = "Find all users", response = UserDto.class)
	@GetMapping
	public List<UserDto> retrieveAll(){
		return userService.retreiveAll();
	}


	@ApiOperation(value = "Find user by id", response = UserDto.class)
	@GetMapping("{userid}")
	public UserDto retrieveUser(@PathVariable long userid){
		return userService.retreive(userid);
	}
}
