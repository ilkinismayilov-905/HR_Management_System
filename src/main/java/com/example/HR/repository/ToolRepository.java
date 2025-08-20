package com.example.HR.repository;

import com.example.HR.entity.employee.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToolRepository extends JpaRepository<Tool,Long> {

    @Query("SELECT t FROM Tool t " +
            "LEFT JOIN FETCH t.skills s " +
            "WHERE t.experience.id = :experienceId")
    List<Tool> findByExperienceIdWithSkills(Long experienceId);

    Optional<Tool> findByName(String name);

    // Experience ID və Tool adına görə tap
    Optional<Tool> findByExperienceIdAndName(Long experienceId, String name);
}
