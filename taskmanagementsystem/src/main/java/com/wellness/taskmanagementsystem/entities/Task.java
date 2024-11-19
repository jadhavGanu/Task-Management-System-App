package com.wellness.taskmanagementsystem.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Title cannot be blank")
	@Size(max = 255, message = "Title should not exceed 255 characters")
	private String title;

	@NotBlank(message = "Description cannot be blank")
	@Size(max = 1000, message = "Description should not exceed 1000 characters")
	private String description;

	@NotNull(message = "Due date cannot be null")
	@FutureOrPresent(message = "Due date must be today or in the future")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dueDate;

	@Enumerated(EnumType.STRING)
	private Status status;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Task() {
		this.status = Status.PENDING;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	// Enum for task status
	public enum Status {
		PENDING, IN_PROGRESS, COMPLETED
	}
}
