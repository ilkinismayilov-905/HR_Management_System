package com.example.HR.service.implement;

import com.example.HR.converter.ToolConverter;
import com.example.HR.dto.tool.ToolRequestDTO;
import com.example.HR.dto.tool.ToolResponseDTO;
import com.example.HR.entity.employee.Tool;
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
        return null;
    }

    @Override
    public ToolResponseDTO getToolById(Long id) {
        return null;
    }

    @Override
    public ToolResponseDTO getToolByName(String name) {
        return null;
    }


    @Override
    public void deleteTool(Long id) {

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
