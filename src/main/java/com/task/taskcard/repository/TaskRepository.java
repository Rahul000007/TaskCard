package com.task.taskcard.repository;

import com.task.taskcard.enums.Priority;
import com.task.taskcard.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByUserUsernameAndIsDeletedFalse(String username, Pageable pageable);

    Page<Task> findByUserUsernameAndPriorityAndIsDeletedFalse(String username, Priority priority, Pageable pageable);

    Page<Task> findByUserUsernameAndDueDateAndIsDeletedFalse(String username, LocalDate dueDate, Pageable pageable);

    Page<Task> findByUserUsernameAndPriorityAndDueDateAndIsDeletedFalse(String username, Priority priority, LocalDate dueDate, Pageable pageable);
}