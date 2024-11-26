package com.task.taskcard.service;

import com.task.taskcard.dto.SubTaskCreateDTO;
import com.task.taskcard.dto.SubTaskDTO;
import com.task.taskcard.dto.SubTaskUpdateDTO;
import com.task.taskcard.model.SubTask;
import org.springframework.data.domain.Page;

public interface SubTaskService {
    SubTaskDTO createSubTask(SubTaskCreateDTO subTaskCreateDTO, String username);

    Page<SubTask> getUserSubTasks(Long taskId, int page, int size, String username);

    SubTaskDTO updateSubTaskStatus(Long subTaskId, SubTaskUpdateDTO subTaskUpdateDTO, String username);

    void deleteSubTask(Long subTaskId, String username);
}
