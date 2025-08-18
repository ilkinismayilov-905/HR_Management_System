package com.example.HR.dto.tool;

import com.example.HR.dto.skill.SkillResponseDTO;

import java.util.Set;

public record ToolResponseDTO(Long id, String name, Set<SkillResponseDTO> skills) {
}
