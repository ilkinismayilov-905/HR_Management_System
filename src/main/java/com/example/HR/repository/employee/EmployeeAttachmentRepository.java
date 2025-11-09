package com.example.HR.repository.employee;

import com.example.HR.entity.employee.EmployeeAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeAttachmentRepository extends JpaRepository<EmployeeAttachment,Long> {
}
