package com.vehicle.repair.exception;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.vehicle.repair.model.dto.ErrorDto;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;


@ControllerAdvice
public class ExceptionHelper extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { ResponseStatusException.class })
	public ResponseEntity<Object> handleInvalidInputException(ResponseStatusException ex) {
		ErrorDto errorDto = ErrorDto.builder()
				.timestamp(new Date())
				.status(ex.getRawStatusCode())
				.errors(Arrays.asList(ex.getMessage()))
				.build();

		return new ResponseEntity<Object>(errorDto, new HttpHeaders(), ex.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
																  HttpHeaders headers,
																  HttpStatus status, WebRequest request) {

		ErrorDto.ErrorDtoBuilder errorResponse = ErrorDto.builder()
				.timestamp(new Date())
				.status(status.value());

		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		errorResponse.errors(errors);

		return new ResponseEntity<>(errorResponse.build(), headers, status);
	}

	@ExceptionHandler(value = { EntityNotFoundException.class })
	public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
		ErrorDto errorDto = ErrorDto.builder()
				.timestamp(new Date())
				.status(HttpStatus.NOT_FOUND.value())
				.errors(Arrays.asList(ex.getLocalizedMessage()))
				.build();

		return new ResponseEntity<Object>(errorDto, HttpStatus.NOT_FOUND);
	}


	@ExceptionHandler(value = { SignatureException.class})
	public ResponseEntity<Object> handleSignatureException(SignatureException ex) {
		ErrorDto errorDto = ErrorDto.builder()
				.timestamp(new Date())
				.status(HttpStatus.UNAUTHORIZED.value())
				.errors(Arrays.asList(ex.getLocalizedMessage()))
				.build();

		return new ResponseEntity<Object>(errorDto, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(value = { ExpiredJwtException.class})
	public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex) {
		ErrorDto errorDto = ErrorDto.builder()
				.timestamp(new Date())
				.status(HttpStatus.UNAUTHORIZED.value())
				.errors(Arrays.asList(ex.getLocalizedMessage()))
				.build();

		return new ResponseEntity<Object>(errorDto, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(value = { NullPointerException.class})
	public ResponseEntity<Object> handleNullException(NullPointerException ex) {
		ErrorDto errorDto = ErrorDto.builder()
				.timestamp(new Date())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.errors(Arrays.asList(ex.getLocalizedMessage()))
				.build();

		return new ResponseEntity<Object>("Null Data Exception", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { Exception.class})
	public ResponseEntity<Object> handleException(Exception ex) {
		ErrorDto errorDto = ErrorDto.builder()
				.timestamp(new Date())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.errors(Arrays.asList(ex.getLocalizedMessage()))
				.build();

		return new ResponseEntity<Object>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
