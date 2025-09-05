package com.example.HR.repository.task;

import com.example.HR.entity.task.TaskAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment,Long> {

    List<TaskAssignment> findByTaskId(Long taskId);
    List<TaskAssignment> findByEmployeeId(Long employeeId);
    Optional<TaskAssignment> findByTask_IdAndEmployee_Id(Long taskId, Long employeeId);

}
