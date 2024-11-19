package com.wellness.taskmanagementsystem.serviceimpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wellness.taskmanagementsystem.entities.Task;
import com.wellness.taskmanagementsystem.exceptions.InvalidTaskDataException;
import com.wellness.taskmanagementsystem.respositories.TaskRepository;
import com.wellness.taskmanagementsystem.servicesimpl.TaskServiceImpl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

	@Mock
	private TaskRepository taskRepository;

	@InjectMocks
	private TaskServiceImpl taskService;

	private Task validTask;

	@BeforeEach
	void setUp() {
		validTask = new Task();
		validTask.setTitle("Test Task");
		validTask.setDescription("Test Description");
		validTask.setStatus(Task.Status.PENDING);
	}

	@Test
	void testCreateTask_ValidTask() {

		when(taskRepository.save(any(Task.class))).thenReturn(validTask);   

		Task createdTask = taskService.createTask(validTask);

		assertNotNull(createdTask);
		assertEquals("Test Task", createdTask.getTitle());
		assertEquals("Test Description", createdTask.getDescription());
		assertEquals(Task.Status.PENDING, createdTask.getStatus());

		verify(taskRepository, times(1)).save(any(Task.class));
	}

	@Test
	void testCreateTask_InvalidTitle() {

		Task invalidTask = new Task();
		invalidTask.setTitle("");
		invalidTask.setDescription("Test Description");
		invalidTask.setStatus(Task.Status.PENDING);

		assertThrows(InvalidTaskDataException.class, () -> taskService.createTask(invalidTask));

		verify(taskRepository, times(0)).save(any(Task.class));
	}
}
