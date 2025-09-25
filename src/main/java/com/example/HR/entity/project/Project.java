package com.example.HR.entity.project;

import com.example.HR.entity.client.Client;
import com.example.HR.entity.employee.Employee;
import com.example.HR.enums.ProjectPriority;
import com.example.HR.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "project")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String projectName;
    private LocalDate timeLine;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ProjectAssignment> projectAssignments = new HashSet<>();

    public Set<Employee> getAssignedEmployees() {
        if (projectAssignments == null) {
            return Collections.emptySet(); // Return an empty set instead of throwing an error
        }
        return projectAssignments.stream()
                .map(ProjectAssignment::getEmployee)
                .collect(Collectors.toSet());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project task = (Project) o;
        return id != null && Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Enumerated(value = EnumType.STRING)
    private ProjectStatus status;

    @Enumerated(value = EnumType.STRING)
    private ProjectPriority priority;

    @Column(nullable = false,name = "description",length = 100)
    private String description;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ProjectAttachment> attachments = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ProjectComment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "client_id",nullable = true)
    private Client client;
}

