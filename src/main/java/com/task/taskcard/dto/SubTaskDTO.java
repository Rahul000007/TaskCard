package com.task.taskcard.dto;

import com.task.taskcard.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubTaskDTO {
    private Long id;
    private String title;
    private Long taskId;
    private Status status;
}
