package com.vehicle.repair.model.form;

import java.io.Serializable;
import java.math.BigDecimal;

import com.vehicle.repair.validation.DateFormat;

import lombok.Data;

@Data
public class MaintenanceUpdateForm implements Serializable {

	private static final long serialVersionUID = -4772210792848486213L;

	@DateFormat(dateFormat = "yyyy-MM-dd HH:mm:ss")
	private String transactionDate;

	private BigDecimal cost;

	private String comments;
}
