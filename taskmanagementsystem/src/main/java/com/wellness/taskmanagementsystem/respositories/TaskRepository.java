package com.wellness.taskmanagementsystem.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wellness.taskmanagementsystem.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
