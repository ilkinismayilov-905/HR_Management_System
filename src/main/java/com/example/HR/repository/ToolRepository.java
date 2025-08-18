package com.example.HR.repository;

import com.example.HR.entity.employee.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends JpaRepository<Tool,Long> {
}
