package com.example.HR.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "dashboard")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long totalEmployees;
    private Long activeTasks;
    private Long completedTasks;
    private Long totalDepartments;
}