package com.example.HR.service;

import com.example.HR.dto.tool.ToolRequestDTO;
import com.example.HR.dto.tool.ToolResponseDTO;

import java.util.List;


public interface ToolService {

    ToolResponseDTO createTool (ToolRequestDTO dto);
    ToolResponseDTO updateTool (Long id,ToolRequestDTO dto);
    ToolResponseDTO getToolById(Long id);
    ToolResponseDTO getToolByName(String name);
//    List<ToolResponseDTO> getAllTools();
    void deleteTool(Long id);
    public List<ToolResponseDTO> getToolsBySkill(String skill);
    public List<ToolResponseDTO> getToolsWithAllSkills();
    boolean toolExists(String name);

}
