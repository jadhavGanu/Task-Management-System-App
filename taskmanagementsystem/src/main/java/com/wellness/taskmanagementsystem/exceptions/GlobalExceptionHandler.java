package com.wellness.taskmanagementsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Handle MethodArgumentNotValidException
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		Map<String, String> errorMessages = new HashMap<>();

		for (FieldError error : result.getFieldErrors()) {
			errorMessages.put(error.getField(), error.getDefaultMessage());
		}

		return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
	}

	// Handle TaskNotFoundException
	@ExceptionHandler(TaskNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleTaskNotFoundException(TaskNotFoundException ex) {
		Map<String, String> error = new HashMap<>();
		error.put("error", ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	// Handle InvalidTaskDataException
	@ExceptionHandler(InvalidTaskDataException.class)
	public ResponseEntity<Map<String, String>> handleInvalidTaskDataException(InvalidTaskDataException ex) {
		Map<String, String> error = new HashMap<>();
		error.put("error", ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	// Handle InvalidDateFormatException
	@ExceptionHandler(InvalidDateFormatException.class)
	public ResponseEntity<Map<String, String>> handleInvalidDateFormatException(InvalidDateFormatException ex) {
		Map<String, String> error = new HashMap<>();
		error.put("error", ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	// Handle JsonMappingException
	@ExceptionHandler(JsonMappingException.class)
	public ResponseEntity<Map<String, String>> handleJsonMappingExceptions(JsonMappingException ex) {
		Map<String, String> errorMessages = new HashMap<>();
		// Catch the DateTimeParseException specifically
		if (ex.getCause() instanceof java.time.format.DateTimeParseException) {
			errorMessages.put("error", "Invalid date format. Please use the format 'yyyy-MM-dd'.");
		} else {
			errorMessages.put("error", "An unexpected error occurred: " + ex.getMessage());
		}
		return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
	}
}
