package com.example.HR.service;

import com.example.HR.dto.payroll.AdditionRequestDTO;
import com.example.HR.dto.payroll.AdditionResponseDTO;

import java.util.List;

public interface AdditionService {
    List<AdditionResponseDTO> getAllAdditions();

    AdditionResponseDTO getAdditionById(Long id);

    AdditionResponseDTO createAddition(AdditionRequestDTO dto);

    AdditionResponseDTO updateAddition(Long id, AdditionRequestDTO dto);

    void deleteAddition(Long id);
}
