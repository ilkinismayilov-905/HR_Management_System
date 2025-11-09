package com.example.HR.repository;

import com.example.HR.entity.employee.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToolRepository extends JpaRepository<Tool,Long> {

    Optional<Tool> findByNameIgnoreCase(String name);

    @Query("SELECT t FROM Tool t JOIN t.skills s WHERE LOWER(s) LIKE LOWER(CONCAT('%', :skill, '%'))")
    List<Tool> findBySkillsContaining(@Param("skill") String skill);

    boolean existsByNameIgnoreCase(String name);


}
