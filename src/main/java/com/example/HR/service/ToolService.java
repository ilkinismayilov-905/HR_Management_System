package com.example.HR.service;


import com.example.HR.dto.tool.ToolRequestDTO;
import com.example.HR.dto.tool.ToolResponseDTO;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

public interface ToolService {
    public ToolRequestDTO save(ToolRequestDTO dto) throws IOException;
    public void deleteById(Long id);
    public Optional<ToolResponseDTO> getById(Long id);
    public ToolResponseDTO update(Long id, ToolRequestDTO updatedDto);
    public List<ToolResponseDTO> getAll() throws MalformedURLException;
}
