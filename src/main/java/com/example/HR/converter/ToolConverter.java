package com.example.HR.converter;

import com.example.HR.dto.tool.ToolRequestDTO;
import com.example.HR.dto.tool.ToolResponseDTO;
import com.example.HR.entity.employee.Tool;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ToolConverter {
    public ToolResponseDTO toResponseDTO(Tool tool) {
        if (tool == null) {
            return null;
        }

        ToolResponseDTO dto = new ToolResponseDTO();
        dto.setId(tool.getId());
        dto.setName(tool.getName());
        dto.setSkills(tool.getSkills() != null ? new ArrayList<>(tool.getSkills()) : new ArrayList<>());

        return dto;
    }

    public Tool requestToEntity(ToolRequestDTO dto){
        if (dto == null) {
            return null;
        }

        Tool tool = new Tool();

        tool.setName(dto.getName());
        tool.setId(tool.getId());
        tool.setSkills(dto.getSkills() != null ? new ArrayList<>(dto.getSkills()) : new ArrayList<>());

        return tool;
    }

    public Tool responseToEntity(ToolResponseDTO dto){
        if (dto == null){
            return null;
        }

        Tool tool = new Tool();

        tool.setId(dto.getId());
        tool.setName(dto.getName());
        tool.setSkills(dto.getSkills() != null ? new ArrayList<>(dto.getSkills()) : new ArrayList<>());

        return tool;
    }

    public void update(ToolRequestDTO dto, Tool entity) {
        if (dto == null || entity == null) {
            return;
        }
        if (dto.getId() != null) entity.setId(dto.getId());
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getSkills() != null) entity.setSkills(dto.getSkills());
    }
}
