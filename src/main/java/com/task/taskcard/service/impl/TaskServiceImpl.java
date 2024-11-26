package com.task.taskcard.service.impl;

import com.task.taskcard.dto.TaskCreateDTO;
import com.task.taskcard.dto.TaskDTO;
import com.task.taskcard.dto.TaskUpdateDTO;
import com.task.taskcard.enums.Priority;
import com.task.taskcard.model.SubTask;
import com.task.taskcard.model.Task;
import com.task.taskcard.model.User;
import com.task.taskcard.repository.SubTaskRepository;
import com.task.taskcard.repository.TaskRepository;
import com.task.taskcard.repository.UserRepository;
import com.task.taskcard.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SubTaskRepository subTaskRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<Task> getUserTasks(String priority, String dueDate, int page, int size, String username) {
        Pageable pageable = PageRequest.of(page, size);

        if (priority == null && dueDate == null) {
            return taskRepository.findByUserUsernameAndIsDeletedFalse(username, pageable);
        }

        if (priority != null && dueDate == null) {
            return taskRepository.findByUserUsernameAndPriorityAndIsDeletedFalse(username, Priority.valueOf(priority.toUpperCase()), pageable);
        }

        if (dueDate != null && priority == null) {
            return taskRepository.findByUserUsernameAndDueDateAndIsDeletedFalse(username, LocalDate.parse(dueDate), pageable);
        }

        return taskRepository.findByUserUsernameAndPriorityAndDueDateAndIsDeletedFalse(username, Priority.valueOf(priority.toUpperCase()), LocalDate.parse(dueDate), pageable);
    }

    @Override
    public TaskDTO createTask(TaskCreateDTO taskCreateDTO, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Task task = new Task();
        task.setTitle(taskCreateDTO.getTitle());
        task.setDescription(taskCreateDTO.getDescription());
        task.setDueDate(taskCreateDTO.getDueDate());
        task.setPriority(taskCreateDTO.getPriority());
        task.setStatus(taskCreateDTO.getStatus());
        task.setUser(user);
        Task createdTask = taskRepository.save(task);

        return convertToDTO(createdTask);
    }

    @Override
    public Task updateTask(Long taskId, TaskUpdateDTO taskUpdateDTO, String username) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!task.getUser().getUsername().equals(username)) {
            throw new SecurityException("You are not authorized to update this task");
        }
        if (taskUpdateDTO.getDueDate() != null) {
            task.setDueDate(taskUpdateDTO.getDueDate());
        }
        if (taskUpdateDTO.getStatus() != null) {
            task.setStatus(taskUpdateDTO.getStatus());
        }
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId, String username) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!task.getUser().getUsername().equals(username)) {
            throw new SecurityException("You are not authorized to delete this task");
        }

        List<SubTask> subTasks = subTaskRepository.findByTaskId(taskId);
        subTasks.forEach(subTask -> {
            subTask.setDeleted(true);
            subTaskRepository.save(subTask);
        });

        task.setDeleted(true);
        taskRepository.save(task);
    }

    @Override
    public TaskDTO convertToDTO(Task task) {

        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getPriority(),
                task.getStatus(),
                task.isDeleted());
    }

}

