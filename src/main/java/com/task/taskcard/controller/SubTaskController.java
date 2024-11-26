package com.task.taskcard.controller;

import com.task.taskcard.dto.SubTaskCreateDTO;
import com.task.taskcard.dto.SubTaskDTO;
import com.task.taskcard.dto.SubTaskUpdateDTO;
import com.task.taskcard.model.SubTask;
import com.task.taskcard.service.AuthService;
import com.task.taskcard.service.SubTaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subTask")
public class SubTaskController {

    @Autowired
    private SubTaskService subTaskService;

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<SubTaskDTO> createSubTask(@Valid @RequestBody SubTaskCreateDTO subTaskCreateDTO) {
        String username = authService.getAuthenticatedUsername();
        SubTaskDTO createdSubTaskDTO = subTaskService.createSubTask(subTaskCreateDTO, username);
        return ResponseEntity.ok(createdSubTaskDTO);
    }


    @GetMapping
    public ResponseEntity<Page<SubTaskDTO>> getAllUserSubTasks(
            @RequestParam(required = false) Long taskId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        String username = authService.getAuthenticatedUsername();
        Page<SubTask> subTasksPage = subTaskService.getUserSubTasks(taskId, page, size, username);

        Page<SubTaskDTO> subTaskDTOs = subTasksPage.map(subTask -> new SubTaskDTO(
                subTask.getId(),
                subTask.getTitle(),
                subTask.getTask().getId(),
                subTask.getStatus()
        ));

        return ResponseEntity.ok(subTaskDTOs);
    }

    @PutMapping("/{subTaskId}")
    public ResponseEntity<SubTaskDTO> updateSubTaskStatus(@PathVariable Long subTaskId, @Valid @RequestBody SubTaskUpdateDTO request) {
        String username = authService.getAuthenticatedUsername();
        SubTaskDTO updatedSubTaskDTO = subTaskService.updateSubTaskStatus(subTaskId, request, username);
        return ResponseEntity.ok(updatedSubTaskDTO);
    }

    @DeleteMapping("/{subTaskId}")
    public ResponseEntity<?> deleteSubTask(@PathVariable Long subTaskId) {
        String username = authService.getAuthenticatedUsername();
        subTaskService.deleteSubTask(subTaskId, username);
        return ResponseEntity.ok("SubTask Deleted");
    }

}
