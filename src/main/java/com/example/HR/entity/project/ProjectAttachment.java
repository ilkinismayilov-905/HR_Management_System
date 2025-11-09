package com.example.HR.entity.project;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "project_attachments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectAttachment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private Long fileSize;

    @CreationTimestamp
    private LocalDateTime uploadedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project",nullable = false)
    private Project project;
}
