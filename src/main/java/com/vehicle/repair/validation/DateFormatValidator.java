package com.vehicle.repair.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class DateFormatValidator implements ConstraintValidator<DateFormat, String> {
	private String dateFormat;


	@Override
	public void initialize(DateFormat constraintAnnotation) {
		this.dateFormat = constraintAnnotation.dateFormat();
	}

	@Override
	public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat(this.dateFormat);
		try
		{
			if(!StringUtils.isEmpty(s)) {
				Date strDate = sdfrmt.parse(s);
			}
		}
		catch (ParseException e)
		{
			return false;
		}

		return true;
	}
}
