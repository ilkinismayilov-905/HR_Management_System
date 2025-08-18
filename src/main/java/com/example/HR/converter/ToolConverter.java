package com.example.HR.converter;

import com.example.HR.dto.skill.SkillRequestDTO;
import com.example.HR.dto.skill.SkillResponseDTO;
import com.example.HR.dto.tool.ToolRequestDTO;
import com.example.HR.dto.tool.ToolResponseDTO;
import com.example.HR.entity.employee.Skill;
import com.example.HR.entity.employee.Tool;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ToolConverter{

    public Tool dtoToEntity(ToolRequestDTO dto) {
        if (dto == null) return null;

        Tool tool = new Tool();
        tool.setId(dto.id());
        tool.setName(dto.name());

        if(dto.skills() != null){
            Set<Skill> skills = dto.skills().stream()
                    .map(skillDto -> {
                Skill skill = new Skill();
                skill.setId(skillDto.id());
                skill.setName(skillDto.name());
                return skill;
            })
                    .collect(Collectors.toSet());
            tool.setSkills(skills);
        }else {
            tool.setSkills(new HashSet<>());
        }

        return tool;
    }

    public List<Tool> dtoListToEntityList (List<ToolRequestDTO> list){
        return list.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }

    public ToolRequestDTO entityToDto(Tool entity){
        Set<SkillRequestDTO> skills = entity.getSkills().stream()
                .map(skill -> new SkillRequestDTO(skill.getId(), skill.getName()))
                .collect(Collectors.toSet());

        return new ToolRequestDTO(entity.getId(), entity.getName(), skills);
    }


    public ToolResponseDTO entityToResponseDto(Tool entity) {
        Set<SkillResponseDTO> skills = entity.getSkills().stream()
                .map(skill -> new SkillResponseDTO(skill.getId(), skill.getName()))
                .collect(Collectors.toSet());

        return new ToolResponseDTO(entity.getId(), entity.getName(), skills);
    }

    public List<ToolResponseDTO> entityListToDtoList (List<Tool> list){
        return list.stream()
                .map(this::entityToResponseDto)
                .collect(Collectors.toList());
    }
}
