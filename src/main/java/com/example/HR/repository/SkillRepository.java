package com.example.HR.repository;

import com.example.HR.entity.employee.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill,Long> {

    List<Skill> findByToolId(Long toolId);

    // Skill adına görə tap
    Optional<Skill> findByName(String name);

    // Tool ID və Skill adına görə tap
    Optional<Skill> findByToolIdAndName(Long toolId, String name);
}
