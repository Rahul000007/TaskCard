package com.task.taskcard.service.impl;

import com.task.taskcard.dto.SubTaskCreateDTO;
import com.task.taskcard.dto.SubTaskDTO;
import com.task.taskcard.dto.SubTaskUpdateDTO;
import com.task.taskcard.model.SubTask;
import com.task.taskcard.model.Task;
import com.task.taskcard.repository.SubTaskRepository;
import com.task.taskcard.repository.TaskRepository;
import com.task.taskcard.service.SubTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubTaskServiceImpl implements SubTaskService {

    @Autowired
    private SubTaskRepository subTaskRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public SubTaskDTO createSubTask(SubTaskCreateDTO subTaskCreateDTO, String username) {
        Task task = taskRepository.findById(subTaskCreateDTO.getTaskId()).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        if (!task.getUser().getUsername().equals(username)) {
            throw new SecurityException("You are not authorized to add subtasks to this task");
        }
        SubTask subTask = new SubTask();
        subTask.setTitle(subTaskCreateDTO.getTitle());
        subTask.setTask(task);
        SubTask createdSubTask = subTaskRepository.save(subTask);
        return convertToDTO(createdSubTask);
    }

    @Override
    public Page<SubTask> getUserSubTasks(Long taskId, int page, int size, String username) {
        Pageable pageable = PageRequest.of(page, size);

        if (taskId != null) {
            Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));
            if (!task.getUser().getUsername().equals(username)) {
                throw new SecurityException("You are not authorized to view sub-tasks for this task");
            }
            return subTaskRepository.findByTaskIdAndTaskUserUsernameAndIsDeletedFalse(taskId, username, pageable);
        }
        return subTaskRepository.findByTaskUserUsernameAndIsDeletedFalse(username, pageable);
    }

    @Override
    public SubTaskDTO updateSubTaskStatus(Long subTaskId, SubTaskUpdateDTO subTaskUpdateDTO, String username) {
        SubTask subTask = subTaskRepository.findById(subTaskId).orElseThrow(() -> new IllegalArgumentException("Subtask not found"));

        if (!subTask.getTask().getUser().getUsername().equals(username)) {
            throw new SecurityException("You are not authorized to update this subtask");
        }

        subTask.setStatus(subTaskUpdateDTO.getStatus());
        SubTask updatedSubTask= subTaskRepository.save(subTask);
        return convertToDTO(updatedSubTask);
    }
    @Override
    @Transactional
    public void deleteSubTask(Long subTaskId, String username) {
        SubTask subTask = subTaskRepository.findById(subTaskId).orElseThrow(() -> new IllegalArgumentException("SubTask not found"));
        if (!subTask.getTask().getUser().getUsername().equals(username)) {
            throw new SecurityException("You are not authorized to delete this subtask");
        }
        subTask.setDeleted(true);
        subTaskRepository.save(subTask);
    }

    public SubTaskDTO convertToDTO(SubTask subTask) {
        return new SubTaskDTO(subTask.getId(), subTask.getTitle(), subTask.getTask().getId(), subTask.getStatus());
    }
}
