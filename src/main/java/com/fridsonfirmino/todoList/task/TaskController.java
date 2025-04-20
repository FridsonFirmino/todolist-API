package com.fridsonfirmino.todoList.task;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fridsonfirmino.todoList.util.Util;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        var idUser = (UUID) request.getAttribute("userId");
        taskModel.setUserId(idUser);

        // Validate the taskModel here (e.g., check for null values, etc.)
        var currentDate = java.time.LocalDateTime.now();

        if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(400).body("A Data de Inicio e de Término devem ser maiores que a Data Actual");
        }

        if(taskModel.getStartAt().isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(400).body("A Data de Inicio deve ser menor que a Data de Término");
        }


        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(201).body(task);
    }

    // Other CRUD methods can be added here
    // For example, updateTask, deleteTask, etc.
    @PutMapping("update/{id}")
    public TaskModel update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
        var task = this.taskRepository.findById(id).orElse(null);
        
        Util.copyNonNullProperties(taskModel, task);
        
        return this.taskRepository.save(task);
    }


    // Listar todas as tarefas de um utilizador
    @GetMapping("/list")
    public ResponseEntity listTaskByUser(HttpServletRequest request){
        var userId = (UUID) request.getAttribute("userId");
        var tasks = this.taskRepository.findByUserId(userId);

        System.out.println("Lista de tasks" + tasks);
        if(tasks.isEmpty()){
            return ResponseEntity.status(400).body("Nenhuma Tarefa encontrada...");
        }
        return ResponseEntity.status(200).body(tasks);
    }
    
}
