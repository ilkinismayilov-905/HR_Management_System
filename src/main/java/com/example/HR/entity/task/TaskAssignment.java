package com.example.HR.entity.task;

import com.example.HR.entity.employee.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "task_assignment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @CreationTimestamp
    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskAssignment)) return false;
        TaskAssignment that = (TaskAssignment) o;

        Long thisEmployeeId = this.employee != null ? this.employee.getId() : null;
        Long thatEmployeeId = that.employee != null ? that.employee.getId() : null;
        Long thisTaskId = this.task != null ? this.task.getId() : null;
        Long thatTaskId = that.task != null ? that.task.getId() : null;

        return Objects.equals(thisEmployeeId, thatEmployeeId) &&
                Objects.equals(thisTaskId, thatTaskId);
    }

    @Override
    public int hashCode() {
        Long employeeId = employee != null ? employee.getId() : null;
        Long taskId = task != null ? task.getId() : null;
        return Objects.hash(employeeId, taskId);
    }
}
