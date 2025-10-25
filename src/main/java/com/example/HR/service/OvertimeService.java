package com.example.HR.service;

import com.example.HR.dto.payroll.AdditionResponseDTO;
import com.example.HR.dto.payroll.OvertimeRequestDTO;
import com.example.HR.dto.payroll.OvertimeResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface OvertimeService {
    public List<OvertimeResponseDTO> getAllOvertimes();
    public OvertimeResponseDTO getOvertimeById(Long id);
    public OvertimeResponseDTO createOvertime(OvertimeRequestDTO overtimeDTO);
    public void deleteOvertime(Long id);
    public OvertimeResponseDTO updateOvertime(Long id, OvertimeRequestDTO overtimeDTO);
    List<OvertimeResponseDTO> getBetweenDates(LocalDate startDate, LocalDate endDate);
}
