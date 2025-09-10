package com.example.HR.entity.task;

import com.example.HR.entity.employee.Employee;
import com.example.HR.entity.ticket.TicketAttachment;
import com.example.HR.entity.ticket.TicketComment;
import com.example.HR.enums.TaskPriority;
import com.example.HR.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "task")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskName;
    private LocalDate timeLine;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<TaskAssignment> taskAssignments = new HashSet<>();

    // Helper method - assigned employees əldə etmək üçün
    public Set<Employee> getAssignedEmployees() {
        if (taskAssignments == null) {
            return Collections.emptySet(); // Return an empty set instead of throwing an error
        }
        return taskAssignments.stream()
                .map(TaskAssignment::getEmployee)
                .collect(Collectors.toSet());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id != null && Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;

    @Enumerated(value = EnumType.STRING)
    private TaskPriority priority;

    @Column(nullable = false,name = "description",length = 100)
    private String description;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<TaskAttachment> attachments = new ArrayList<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<TaskComment> comments = new ArrayList<>();

}
