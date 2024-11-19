package com.wellness.taskmanagementsystem.services;

import java.util.List;

import com.wellness.taskmanagementsystem.entities.Task;

public interface TaskService {

	List<Task> getAllTasks();

	Task getTaskById(Long id);

	Task createTask(Task task);

	Task updateTask(Long id, Task updatedTask);

	void deleteTask(Long id);

	Task markAsComplete(Long id);
}
