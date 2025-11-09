package com.example.HR.entity.employee;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Table(name = "tools")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 25)
    private String name;

    @ElementCollection
    @CollectionTable(name = "tool_skills", joinColumns = @JoinColumn(name = "tool_id"))
    @Column(name = "skill_name")
    private List<String> skills;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
