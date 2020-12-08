package com.vehicle.repair.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.vehicle.repair.model.dto.VehicleDto;
import com.vehicle.repair.model.form.VehicleCreateForm;
import com.vehicle.repair.persistence.entity.User;
import com.vehicle.repair.persistence.entity.Vehicle;
import com.vehicle.repair.persistence.repository.UserRepository;
import com.vehicle.repair.persistence.repository.VehicleRepository;
import com.vehicle.repair.util.AuthenticationUtil;
import com.vehicle.repair.util.EntityDtoMapperUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleService {

	private final VehicleRepository vehicleRepository;
	private final UserRepository userRepository;
	private final EntityDtoMapperUtil modelMapper;
	private final AuthenticationUtil authenticationUtil;

	public VehicleDto create(VehicleCreateForm vehicleCreateForm){
		Vehicle vehicle = new Vehicle();
		vehicle.setBrand(vehicleCreateForm.getBrand());
		vehicle.setModel(vehicleCreateForm.getModel());
		vehicle.setUser(authenticationUtil.getAuthenticatedUser());

		vehicleRepository.save(vehicle);

		return modelMapper.map(vehicle, VehicleDto.class);
	}

	public List<VehicleDto> retrieveAll(){
		List<Vehicle> vehicles = vehicleRepository.findAll();

		if(vehicles.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no vehicle found");

		return modelMapper.mapAll(vehicles, VehicleDto.class);
	}

	public List<VehicleDto> retrieveOwned(){
		User user = authenticationUtil.getAuthenticatedUser();

		List<Vehicle> vehicles = vehicleRepository.findAllByUser(user);

		if(vehicles.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no vehicle found");

		return modelMapper.mapAll(vehicles, VehicleDto.class);
	}

	public VehicleDto retrieve(long id){
		Optional<Vehicle> vehicle = vehicleRepository.findById(id);

		if(!vehicle.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no vehicle found");

		return modelMapper.map(vehicle, VehicleDto.class);
	}

	public VehicleDto sell(long vehicleId, long buyerId){
		Vehicle vehicle = vehicleRepository.getOne(vehicleId);

		User authenticatedUser = authenticationUtil.getAuthenticatedUser();

		if(vehicle.getUser().getId() !=  authenticatedUser.getId())
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "only owner can sell");

		User buyer = userRepository.getOne(buyerId);

		if(buyer.getId() != authenticatedUser.getId()){
			vehicle.setUser(buyer);
		}

		return modelMapper.map(vehicleRepository.save(vehicle), VehicleDto.class);
	}
}
