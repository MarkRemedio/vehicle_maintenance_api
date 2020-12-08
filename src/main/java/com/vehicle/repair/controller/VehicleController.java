package com.vehicle.repair.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.repair.model.dto.VehicleDto;
import com.vehicle.repair.model.form.VehicleCreateForm;
import com.vehicle.repair.service.VehicleService;

import lombok.RequiredArgsConstructor;
import io.swagger.annotations.ApiOperation;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/api/v01/vehicle"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class VehicleController {

	private final VehicleService vehicleService;

	@ApiOperation(value = "create vehicle", notes = "assign the vehicle to the logged-in user",response = VehicleDto.class)
	@PostMapping
	public VehicleDto create(@Valid @RequestBody VehicleCreateForm vehicleCreateForm){
		return vehicleService.create(vehicleCreateForm);
	}


	@ApiOperation(value = "find vehicle", notes = "all owned vehicle", response = VehicleDto.class, responseContainer = "List")
	@GetMapping
	public List<VehicleDto> retrieveAll(){
		return vehicleService.retrieveOwned();
	}


	@ApiOperation(value = "find vehicle by id", notes = "can check not owned vehicles", response = VehicleDto.class)
	@GetMapping("{id}")
	public VehicleDto retrieve(@PathVariable long id){
		return vehicleService.retrieve(id);
	}


	@ApiOperation(value = "sell vehicle", notes = "only owner can sell", response = VehicleDto.class)
	@PutMapping("{vehicleId}/sell/{buyerId}")
	public VehicleDto sell(@PathVariable long vehicleId,
						   @PathVariable long buyerId){
		return vehicleService.sell(vehicleId, buyerId);
	}
}
