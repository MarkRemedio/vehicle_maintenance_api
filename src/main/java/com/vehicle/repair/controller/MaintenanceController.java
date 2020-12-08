package com.vehicle.repair.controller;

import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.repair.model.dto.MaintenanceDto;
import com.vehicle.repair.model.form.MaintenanceCreateForm;
import com.vehicle.repair.model.form.MaintenanceUpdateForm;
import com.vehicle.repair.service.MaintenanceService;

import lombok.RequiredArgsConstructor;
import io.swagger.annotations.ApiOperation;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/api/v01/maintenance"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class MaintenanceController {

	private final MaintenanceService maintenanceService;

	@ApiOperation(value = "Create maintenance to a vehicle", notes = "vehicle must exist", response = MaintenanceDto.class)
	@PostMapping("{vehicleId}")
	public MaintenanceDto create(@Valid @RequestBody MaintenanceCreateForm maintenanceCreateForm,
							  @PathVariable long vehicleId) throws ParseException {
		return maintenanceService.create(maintenanceCreateForm, vehicleId);
	}


	@ApiOperation(value = "All maintenance of login user", notes = "only shows maintenaces of the logged-in user",
			response = MaintenanceDto.class,
			responseContainer = "List")
	@GetMapping
	public List<MaintenanceDto> retrieveAll() {
		return maintenanceService.retreiveAll();
	}


	@ApiOperation(value = "Find maintenance by id", notes = "vehicle must exist", response = MaintenanceDto.class)
	@GetMapping("{maintenanceid}")
	public MaintenanceDto retrieve(@PathVariable long maintenanceid) {
		return maintenanceService.retrieve(maintenanceid);
	}


	@ApiOperation(value = "Update maintenance by id", notes = "only vehicle owner can update", response = MaintenanceDto.class)
	@PatchMapping("{maintenanceid}")
	public MaintenanceDto update(@Valid @RequestBody MaintenanceUpdateForm maintenanceUpdateForm,
								 @PathVariable long maintenanceid) throws ParseException {
		return maintenanceService.update(maintenanceUpdateForm, maintenanceid);
	}


	@ApiOperation(value = "Delete maintenance by id", notes = "set delete flag to true", response = MaintenanceDto.class)
	@DeleteMapping("{maintenanceid}")
	public MaintenanceDto delete(@PathVariable long maintenanceid){
		return maintenanceService.delete(maintenanceid);
	}
}
