package com.example.HR.service.implement;

import com.example.HR.converter.ToolConverter;
import com.example.HR.dto.tool.ToolRequestDTO;
import com.example.HR.dto.tool.ToolResponseDTO;
import com.example.HR.entity.employee.Tool;
import com.example.HR.exception.NoIDException;
import com.example.HR.exception.NotFoundException;
import com.example.HR.exception.ValidException;
import com.example.HR.repository.ToolRepository;
import com.example.HR.service.ToolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ToolServiceImpl implements ToolService {

    private final ToolRepository toolRepository;
    private final ToolConverter toolConverter;

    @Override
    public ToolResponseDTO createTool(ToolRequestDTO dto) {
        log.info("Creating new tool: {}", dto.getName());

        if(toolRepository.existsByNameIgnoreCase(dto.getName())){
            throw new ValidException("Tool with name ' " +dto.getName() + "' already exists");
        }

        Tool tool = toolConverter.requestToEntity(dto);
        Tool savedTool = toolRepository.save(tool);

        log.info("Tool created successfully with ID: {}", savedTool.getId());

        return toolConverter.toResponseDTO( savedTool);

    }

    @Override
    public ToolResponseDTO updateTool(Long id, ToolRequestDTO dto) {

        log.info("Updating tool with given ID: {}" ,id);

        Tool entity = toolRepository.findById(id)
                .orElseThrow(() -> new NoIDException("Tool not found by id: " + id));

        Tool toolWithSameName = toolRepository.findByNameIgnoreCase(dto.getName()).orElse(null);
        if (toolWithSameName != null && !toolWithSameName.getId().equals(id)) {
            throw new RuntimeException("Another tool with name '" + dto.getName() + "' already exists");
        }

        toolConverter.update(dto,entity);

        Tool savedTool = toolRepository.save(entity);

        log.info("Tool updated with ID: {}", id);

        return toolConverter.toResponseDTO(savedTool);
    }

    @Override
    @Transactional(readOnly = true)
    public ToolResponseDTO getToolById(Long id) {
        log.info("Getting tool by id: {}" , id);

        Tool tool = toolRepository.findById(id)
                .orElseThrow(() -> new NoIDException("Tool not found with ID: " + id));

        return toolConverter.toResponseDTO(tool);

    }

    @Override
    public ToolResponseDTO getToolByName(String name) {
        log.info("Fetching tool with name: {}", name);

        Tool tool = toolRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new NotFoundException("Tool not found by name: " + name));

        return toolConverter.toResponseDTO(tool);
    }


    @Override
    public void deleteTool(Long id) {

        log.info("Deleting tool by id: {}", id);
        if (!toolRepository.existsById(id)) {
            throw new RuntimeException("Tool not found with ID: " + id);
        }
        toolRepository.deleteById(id);

        log.info("Tool deleted successfully with ID: {}", id);

    }

    @Override
    @Transactional(readOnly = true)
    public List<ToolResponseDTO> getToolsBySkill(String skill){

        log.info("Fetching tools by skill: {}", skill);

        List<Tool> tools = toolRepository.findBySkillsContaining(skill);
        return tools.stream()
                .map(toolConverter::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ToolResponseDTO> getToolsWithAllSkills() {
        log.info("Fetching all tools with their skills");
        List<Tool> tools = toolRepository.findAll();
        return tools.stream()
                .map(toolConverter::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean toolExists(String name) {
        return false;
    }
}
