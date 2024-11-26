package com.task.taskcard.model;

import com.task.taskcard.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class SubTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Status status;

    private boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
}

