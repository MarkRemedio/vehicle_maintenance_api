package com.vehicle.repair.model.form;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class VehicleCreateForm implements Serializable {

	private static final long serialVersionUID = 3451024327776508634L;

	@NotBlank(message = "brand should not be empty")
	private String brand;

	@NotBlank(message = "model should not be empty")
	private String model;
}
