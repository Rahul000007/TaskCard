package com.task.taskcard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubTaskCreateDTO {
    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Task ID is required")
    private Long taskId;
}
