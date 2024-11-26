package com.task.taskcard.dto;


import com.task.taskcard.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubTaskUpdateDTO {
    @NotNull(message = "Status is required")
    private Status status;
}
