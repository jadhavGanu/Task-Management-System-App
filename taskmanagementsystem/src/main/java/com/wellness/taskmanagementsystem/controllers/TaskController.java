package com.wellness.taskmanagementsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wellness.taskmanagementsystem.entities.Task;
import com.wellness.taskmanagementsystem.exceptions.TaskNotFoundException;
import com.wellness.taskmanagementsystem.services.TaskService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;

	// Get all tasks.
	@GetMapping
	public ResponseEntity<List<Task>> getAllTasks() {
		List<Task> tasks = taskService.getAllTasks();
		return ResponseEntity.ok(tasks);
	}

	// Get a task by its ID.
	@GetMapping("/{id}")
	public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
		Task task = taskService.getTaskById(id);
		if (task == null) {
			throw new TaskNotFoundException("Task with ID " + id + " not found");
		}
		return ResponseEntity.ok(task);
	}

	// Create a new task.
	@PostMapping
	public ResponseEntity<Task> createTask(@RequestBody @Valid Task task) {
		Task createdTask = taskService.createTask(task);
		return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
	}

	// Update an existing task.
	@PutMapping("/{id}")
	public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody @Valid Task task) {
		Task existingTask = taskService.getTaskById(id);
		if (existingTask == null) {
			throw new TaskNotFoundException("Task with ID " + id + " not found");
		}
		Task updatedTask = taskService.updateTask(id, task);
		return ResponseEntity.ok(updatedTask);
	}

	// Delete a task by its ID.
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
		Task existingTask = taskService.getTaskById(id);
		if (existingTask == null) {
			throw new TaskNotFoundException("Task with ID " + id + " not found");
		}
		taskService.deleteTask(id);
		return ResponseEntity.noContent().build();
	}

	// Mark a task as complete.
	@PatchMapping("/{id}/complete")
	public ResponseEntity<Task> markTaskAsComplete(@PathVariable Long id) {
		Task existingTask = taskService.getTaskById(id);
		if (existingTask == null) {
			throw new TaskNotFoundException("Task with ID " + id + " not found");
		}
		Task updatedTask = taskService.markAsComplete(id);
		return ResponseEntity.ok(updatedTask);
	}
}
