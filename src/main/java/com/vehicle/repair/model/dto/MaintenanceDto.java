package com.vehicle.repair.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class MaintenanceDto {

	private long id;

	private Date transactionDate;

	private BigDecimal cost;

	private String comments;

	private boolean isDeleted;

	private int vehicleId;
}
