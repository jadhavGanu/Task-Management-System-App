package com.wellness.taskmanagementsystem.servicesimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellness.taskmanagementsystem.entities.Task;
import com.wellness.taskmanagementsystem.exceptions.InvalidDateFormatException;
import com.wellness.taskmanagementsystem.exceptions.InvalidTaskDataException;
import com.wellness.taskmanagementsystem.exceptions.TaskNotFoundException;
import com.wellness.taskmanagementsystem.respositories.TaskRepository;
import com.wellness.taskmanagementsystem.services.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	@Override
	public Task getTaskById(Long id) {
		return taskRepository.findById(id)
				.orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found"));
	}

	@Override
	public Task createTask(Task task) {
		if (task.getTitle() == null || task.getTitle().isEmpty()) {
			throw new InvalidTaskDataException("Task title is required");
		}
		try {
			LocalDate dueDate = task.getDueDate();
			if (dueDate != null) {
				task.setDueDate(dueDate);
			}
		} catch (DateTimeParseException e) {
			throw new InvalidDateFormatException("Invalid date format for dueDate. Please use 'yyyy-MM-dd'");
		}
		task.setCreatedAt(LocalDateTime.now());
		task.setUpdatedAt(LocalDateTime.now());
		return taskRepository.save(task);
	}

	@Override
	public Task updateTask(Long id, Task updatedTask) {
		Task existingTask = getTaskById(id);
		existingTask.setTitle(updatedTask.getTitle());
		existingTask.setDescription(updatedTask.getDescription());

		try {
			LocalDate dueDate = updatedTask.getDueDate();
			if (dueDate != null) {
				existingTask.setDueDate(dueDate);
			}
		} catch (DateTimeParseException e) {
			throw new InvalidDateFormatException("Invalid date format for dueDate. Please use 'yyyy-MM-dd'");
		}

		existingTask.setStatus(updatedTask.getStatus());
		existingTask.setUpdatedAt(LocalDateTime.now());

		return taskRepository.save(existingTask);
	}

	@Override
	public void deleteTask(Long id) {
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found"));
		taskRepository.delete(task);
	}

	@Override
	public Task markAsComplete(Long id) {
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found"));
		task.setStatus(Task.Status.COMPLETED);
		task.setUpdatedAt(LocalDateTime.now());
		return taskRepository.save(task);
	}
}