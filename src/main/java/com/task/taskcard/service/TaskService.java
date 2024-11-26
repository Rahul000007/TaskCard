package com.task.taskcard.service;

import com.task.taskcard.dto.TaskCreateDTO;
import com.task.taskcard.dto.TaskDTO;
import com.task.taskcard.dto.TaskUpdateDTO;
import com.task.taskcard.model.Task;
import org.springframework.data.domain.Page;

public interface TaskService {
    Page<Task> getUserTasks(String priority, String dueDate, int page, int size, String username);

    TaskDTO createTask(TaskCreateDTO taskCreateDTO, String username);

    Task updateTask(Long taskId, TaskUpdateDTO taskUpdateDTO, String username);

    void deleteTask(Long taskId, String username);

    TaskDTO convertToDTO(Task task);
}
