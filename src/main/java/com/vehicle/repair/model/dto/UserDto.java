package com.vehicle.repair.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserDto implements Serializable {
	private static final long serialVersionUID = -6907533164277279059L;

	private long id;
	private String username;
	private String firstname;
	private String middlename;
	private String lastname;
}
