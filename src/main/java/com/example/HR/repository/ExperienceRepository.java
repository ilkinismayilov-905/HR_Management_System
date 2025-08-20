package com.example.HR.repository;

import com.example.HR.entity.employee.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience,Long> {

    @Query("SELECT DISTINCT e FROM Experience e " +
            "LEFT JOIN FETCH e.tools t " +
            "LEFT JOIN FETCH t.skills s")
    List<Experience> findAllExperiences();

    @Query("SELECT e FROM Experience e " +
            "LEFT JOIN FETCH e.tools t " +
            "LEFT JOIN FETCH t.skills s " +
            "WHERE e.id = :id")
    Optional<Experience> findByIdExperiences(Long id);
}
