package com.example.HR.entity.task;

import com.example.HR.entity.employee.Employee;
import com.example.HR.entity.ticket.TicketAttachment;
import com.example.HR.entity.ticket.TicketComment;
import com.example.HR.enums.TaskPriority;
import com.example.HR.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<TaskAssignment> taskAssignments = new HashSet<>();


    public Set<Employee> getAssignedEmployees() {
        return taskAssignments.stream()
                .map(TaskAssignment::getEmployee)
                .collect(Collectors.toSet());
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
