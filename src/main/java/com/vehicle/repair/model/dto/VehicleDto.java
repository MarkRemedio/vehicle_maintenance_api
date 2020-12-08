package com.vehicle.repair.model.dto;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;

@Data
public class VehicleDto implements Serializable {
	private static final long serialVersionUID = 4528116439614025027L;

	private long id;

	private String brand;

	private String model;

	private UserDto user;

	private Set<MaintenanceDto> maintenances;
}
