package com.task.taskcard.repository;

import com.task.taskcard.model.SubTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubTaskRepository extends JpaRepository<SubTask, Long> {

    List<SubTask> findByTaskId(Long taskId);

    Page<SubTask> findByTaskIdAndTaskUserUsernameAndIsDeletedFalse(Long taskId, String username, Pageable pageable);

    Page<SubTask> findByTaskUserUsernameAndIsDeletedFalse(String username, Pageable pageable);
}