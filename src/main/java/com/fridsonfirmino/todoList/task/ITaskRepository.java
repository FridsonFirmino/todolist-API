package com.fridsonfirmino.todoList.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID>{
    // Custom query methods can be defined here if needed
    // For example, findByUserId(UUID userId);
    // List<TaskModel> findByUserId(UUID userId);
    // List<TaskModel> findByStatus(String status);
    // List<TaskModel> findByPriority(String priority);
    // List<TaskModel> findByStartAtBetween(LocalDateTime start, LocalDateTime end);
    
}
