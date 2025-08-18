package com.example.HR.dto.tool;

import com.example.HR.dto.skill.SkillRequestDTO;

import java.util.Set;

public record ToolRequestDTO(
        Long id,
        String name,
        Set<SkillRequestDTO> skills
) {}
