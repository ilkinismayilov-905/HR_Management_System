package com.example.HR.service;

import com.example.HR.dto.payroll.deduction.DeductionRequestDTO;
import com.example.HR.dto.payroll.deduction.DeductionResponseDTO;
import com.example.HR.dto.payroll.overtime.OvertimeRequestDTO;
import com.example.HR.dto.payroll.overtime.OvertimeResponseDTO;
import com.example.HR.enums.payroll.DeductionRate;

import java.time.LocalDate;
import java.util.List;

public interface DeductionService {
    public List<DeductionResponseDTO> getAll();
    public DeductionResponseDTO getDeductionById(Long id);
    public DeductionResponseDTO createDeduction(DeductionRequestDTO deductionDTO);
    public void deleteDeduction(Long id);
    public DeductionResponseDTO updateDeduction(Long id, DeductionRequestDTO deductionDTO);
    List<DeductionResponseDTO> getBetweenDates(LocalDate startDate, LocalDate endDate);
    List<DeductionResponseDTO> getByRate(DeductionRate deductionRate);
}
