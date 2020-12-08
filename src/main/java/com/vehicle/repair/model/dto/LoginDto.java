package com.vehicle.repair.model.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class LoginDto implements Serializable {
	private static final long serialVersionUID = 3041451762478090584L;

	private String token;
	private Date tokenExpiration;
	private UserDto user;
}
