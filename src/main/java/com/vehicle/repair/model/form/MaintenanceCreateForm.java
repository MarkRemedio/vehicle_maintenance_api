package com.vehicle.repair.model.form;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.vehicle.repair.validation.DateFormat;

import lombok.Data;

@Data
public class MaintenanceCreateForm implements Serializable {

	private static final long serialVersionUID = -6460811753746154067L;

	@NotBlank(message = "transaction date should not be empty")
	@DateFormat(dateFormat = "yyyy-MM-dd HH:mm:ss")
	private String transactionDate;

	@NotNull(message = "cost shoudl not be null")
	private BigDecimal cost;

	@NotBlank(message = "comments should not be empty")
	private String comments;

}
