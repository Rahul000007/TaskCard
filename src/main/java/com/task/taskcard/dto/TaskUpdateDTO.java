package com.task.taskcard.dto;

import com.task.taskcard.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TaskUpdateDTO {
    private LocalDate dueDate;
    private Status status;
}
