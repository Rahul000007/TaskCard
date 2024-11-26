package com.task.taskcard.controller;

import com.task.taskcard.dto.*;
import com.task.taskcard.model.Task;
import com.task.taskcard.service.AuthService;
import com.task.taskcard.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskCreateDTO taskCreateDTO) {
        String username = authService.getAuthenticatedUsername();
        TaskDTO taskDTO = taskService.createTask(taskCreateDTO, username);
        return ResponseEntity.ok(taskDTO);
    }


    @GetMapping
    public ResponseEntity<Page<TaskDTO>> getAllUserTasks(@RequestParam(required = false) String priority,
                                                         @RequestParam(required = false) String dueDate,
                                                         @RequestParam(defaultValue = "0") Integer page,
                                                         @RequestParam(defaultValue = "10") Integer size) {

        String username = authService.getAuthenticatedUsername();
        Page<Task> tasksPage = taskService.getUserTasks(priority, dueDate, page, size, username);

        Page<TaskDTO> taskDTOs = tasksPage.map(task -> new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getPriority(),
                task.getStatus(),
                task.isDeleted()
        ));

        return ResponseEntity.ok(taskDTOs);
    }


    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long taskId, @RequestBody TaskUpdateDTO taskUpdateDTO) {
        String username = authService.getAuthenticatedUsername();
        Task updatedTask = taskService.updateTask(taskId, taskUpdateDTO, username);
        TaskDTO taskDTO=taskService.convertToDTO(updatedTask);
        return ResponseEntity.ok(taskDTO);
    }


    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        String username = authService.getAuthenticatedUsername();
        taskService.deleteTask(taskId, username);
        return ResponseEntity.ok("Task Deleted");
    }
}

