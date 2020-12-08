package com.vehicle.repair.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.vehicle.repair.model.dto.UserDto;
import com.vehicle.repair.persistence.entity.User;
import com.vehicle.repair.persistence.repository.UserRepository;
import com.vehicle.repair.util.EntityDtoMapperUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final EntityDtoMapperUtil modelMapper;

	public List<UserDto> retreiveAll(){
		List<User> users = userRepository.findAll();

		if(users.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no users found");

 		return modelMapper.mapAll(users, UserDto.class);
	}


	public UserDto retreive(long id){
		Optional<User> user = userRepository.findById(id);

		if(!user.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no user found");

		return modelMapper.map(user, UserDto.class);
	}

}
