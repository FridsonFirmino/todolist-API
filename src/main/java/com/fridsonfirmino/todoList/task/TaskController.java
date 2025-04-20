package com.fridsonfirmino.todoList.task;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        System.out.println("Chegou no Filtro no Controller");
        var idUser = (UUID) request.getAttribute("userId");
        taskModel.setUserId(idUser);
        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(201).body(task);
    }

    // Other CRUD methods can be added here
    
}
