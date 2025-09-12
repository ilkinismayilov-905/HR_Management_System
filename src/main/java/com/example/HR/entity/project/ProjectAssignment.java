package com.example.HR.entity.project;

import com.example.HR.entity.employee.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "project_assignment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @CreationTimestamp
    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectAssignment)) return false;
        ProjectAssignment that = (ProjectAssignment) o;

        Long thisEmployeeId = this.employee != null ? this.employee.getId() : null;
        Long thatEmployeeId = that.employee != null ? that.employee.getId() : null;
        Long thisTaskId = this.project != null ? this.project.getId() : null;
        Long thatTaskId = that.project != null ? that.project.getId() : null;

        return Objects.equals(thisEmployeeId, thatEmployeeId) &&
                Objects.equals(thisTaskId, thatTaskId);
    }

    @Override
    public int hashCode() {
        Long employeeId = employee != null ? employee.getId() : null;
        Long taskId = project != null ? project.getId() : null;
        return Objects.hash(employeeId, taskId);
    }
}
