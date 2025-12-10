package com.example.HR.repository.task;

import com.example.HR.entity.task.Task;
import com.example.HR.enums.TaskStatus;
import com.example.HR.record.GanttTaskDto;
import com.example.HR.record.ProjectProgressDto;
import com.example.HR.record.StatusCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    @Query("SELECT t FROM Task t JOIN t.taskAssignments ta WHERE ta.employee.id = :employeeId")
    List<Task> findTasksByEmployeeId(@Param("employeeId") Long employeeId);

    List<Task> findByStatus(TaskStatus status);
}
