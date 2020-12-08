package com.vehicle.repair.model.form;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class RegisterForm implements Serializable {

	private static final long serialVersionUID = -4979435607130732092L;

	@NotBlank(message = "username should not be empty")
	private String username;

	@NotBlank(message = "password should not be empty")
	private String password;

	@NotBlank(message = "firstname should not be empty")
	private String firstname;

	@NotBlank(message = "middlename should not be empty")
	private String middlename;

	@NotBlank(message = "lastname should not be empty")
	private String lastname;


}
