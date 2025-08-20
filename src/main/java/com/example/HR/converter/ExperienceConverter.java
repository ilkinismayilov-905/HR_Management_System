package com.example.HR.converter;

import com.example.HR.dto.experience.ExperienceRequestDTO;
import com.example.HR.dto.experience.ExperienceResponseDTO;
import com.example.HR.dto.skill.SkillResponseDTO;
import com.example.HR.dto.tool.ToolResponseDTO;
import com.example.HR.entity.employee.Experience;
import com.example.HR.entity.employee.Skill;
import com.example.HR.entity.employee.Tool;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ExperienceConverter {

    public ExperienceResponseDTO toResponseDTO(Experience experience){
        if(experience == null){
            return null;
        }

        Map<String, Set<String>> experiences = new HashMap<>();

        if(experience.getTools() != null){
            for (Tool tool : experience.getTools()){
                Set<String> skillNames = new HashSet<>();
                if(tool.getSkills() != null){
                    skillNames = tool.getSkills().stream()
                            .map(Skill::getName)
                            .collect(Collectors.toSet());
                }
                experiences.put(tool.getName(),skillNames);
            }
        }

        return ExperienceResponseDTO.builder()
                .id(experience.getId())
                .experiences(experiences)
                .build();
    }

    public Experience toEntity(ExperienceRequestDTO dto){
        if(dto == null){
            return null;
        }

        Experience experience = new Experience();
        Set<Tool> tools = new HashSet<>();

        if(dto.getExperiences() != null){
            for (Map.Entry<String,Set<String>> entry : dto.getExperiences().entrySet()){
                Tool tool = new Tool();
                tool.setName(entry.getKey());
                tool.setExperience(experience);

                Set<Skill> skills = new HashSet<>();
                if(entry.getValue() != null){
                    for (String skillName : entry.getValue()){
                        Skill skill = new Skill();
                        skill.setName(skillName);
                        skill.setTool(tool);
                        skills.add(skill);
                    }
                }

                tool.setSkills(skills);
                tools.add(tool);
            }
        }

        experience.setTools(tools);
        return experience;
    }

    public List<ExperienceResponseDTO> toResponseDTOList(List<Experience> experiences) {
        if (experiences == null) {
            return new ArrayList<>();
        }

        return experiences.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ToolResponseDTO toToolResponseDTO(Tool tool) {
        if (tool == null) {
            return null;
        }

        Set<String> skillNames = new HashSet<>();
        if (tool.getSkills() != null) {
            skillNames = tool.getSkills().stream()
                    .map(Skill::getName)
                    .collect(Collectors.toSet());
        }

        return ToolResponseDTO.builder()
                .id(tool.getId())
                .name(tool.getName())
                .skills(skillNames)
                .build();
    }

    public SkillResponseDTO toSkillResponseDTO(Skill skill) {
        if (skill == null) {
            return null;
        }

        return SkillResponseDTO.builder()
                .id(skill.getId())
                .name(skill.getName())
                .build();
    }
}
