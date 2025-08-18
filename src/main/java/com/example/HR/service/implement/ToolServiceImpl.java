package com.example.HR.service.implement;

import com.example.HR.converter.ToolConverter;
import com.example.HR.dto.skill.SkillResponseDTO;
import com.example.HR.dto.tool.ToolRequestDTO;
import com.example.HR.dto.tool.ToolResponseDTO;
import com.example.HR.entity.employee.Tool;
import com.example.HR.repository.ToolRepository;
import com.example.HR.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ToolServiceImpl implements ToolService {

    private final ToolRepository toolRepository;
    private final ToolConverter converter;

    @Autowired
    public ToolServiceImpl(ToolRepository toolRepository, ToolConverter converter) {
        this.toolRepository = toolRepository;
        this.converter = converter;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public ToolRequestDTO save(ToolRequestDTO dto) throws IOException {
        Tool converted = converter.dtoToEntity(dto);

        return converter.entityToDto(toolRepository.save(converted));
    }

    @Override
    public Optional<ToolResponseDTO> getById(Long id) {
        return Optional.empty();
    }


    @Override
    public ToolResponseDTO update(Long id, ToolRequestDTO updatedDto) {
        return null;
    }

    @Override
    public List<ToolResponseDTO> getAll() throws MalformedURLException {
        return toolRepository.findAll().stream()
                .map(tool -> new ToolResponseDTO(
                        tool.getId(),
                        tool.getName(),
                        tool.getSkills().stream()
                                .map(skill -> new SkillResponseDTO(
                                        skill.getId(),
                                        skill.getName()))
                                .collect(Collectors.toSet())
                ))
                .toList();
    }
}
