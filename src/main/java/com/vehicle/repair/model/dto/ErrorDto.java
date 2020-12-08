package com.vehicle.repair.model.dto;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
	private Date timestamp;
	private int status;
	private List<String> errors;
}
