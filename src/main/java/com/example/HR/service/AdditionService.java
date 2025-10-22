package com.example.HR.service;

import com.example.HR.dto.calendar.CalendarResponseDTO;
import com.example.HR.dto.payroll.AdditionRequestDTO;
import com.example.HR.dto.payroll.AdditionResponseDTO;
import com.example.HR.enums.payroll.AdditionCategory;

import java.time.LocalDate;
import java.util.List;

public interface AdditionService {
    List<AdditionResponseDTO> getAllAdditions();
    AdditionResponseDTO getAdditionById(Long id);
    AdditionResponseDTO createAddition(AdditionRequestDTO dto);
    AdditionResponseDTO updateAddition(Long id, AdditionRequestDTO dto);
    List<AdditionResponseDTO> getAdditionsByCategory(AdditionCategory category);
    void deleteAddition(Long id);
    List<AdditionResponseDTO> getBetweenDates(LocalDate startDate, LocalDate endDate);
}
