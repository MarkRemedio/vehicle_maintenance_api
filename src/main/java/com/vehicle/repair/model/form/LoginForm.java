package com.vehicle.repair.model.form;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginForm implements Serializable {
	private static final long serialVersionUID = -324277560499929010L;

	@NotBlank(message = "username should not be empty")
	private String username;

	@NotBlank(message = "password should not be empty")
	private String password;
}
