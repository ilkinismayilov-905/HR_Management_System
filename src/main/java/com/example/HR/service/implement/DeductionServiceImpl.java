package com.example.HR.service.implement;

import com.example.HR.converter.payroll.DeductionConverter;
import com.example.HR.dto.payroll.deduction.DeductionRequestDTO;
import com.example.HR.dto.payroll.deduction.DeductionResponseDTO;
import com.example.HR.entity.payroll.Deduction;
import com.example.HR.enums.payroll.DeductionRate;
import com.example.HR.exception.NotFoundException;
import com.example.HR.repository.payroll.DeductionRepository;
import com.example.HR.service.DeductionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class DeductionServiceImpl implements DeductionService {

    private final DeductionRepository deductionRepository;
    private final DeductionConverter converter;

    @Override
    public List<DeductionResponseDTO> getAll() {
        log.info("Getting all deductions");
        return converter.toResponseDeductionList(deductionRepository.findAll());
    }

    @Override
    public DeductionResponseDTO getDeductionById(Long id) {
        Deduction deduction = deductionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Deduction with id " + id + " not found!"));

        log.info("Getting deduction by id {}", deduction.getId());
        return converter.toResponseDeduction(deduction);
    }

    @Override
    public DeductionResponseDTO createDeduction(DeductionRequestDTO deductionDTO) {
        Deduction convertedDeduction = converter.toEntityDeduction(deductionDTO);
        Deduction deduction = deductionRepository.save(convertedDeduction);
        log.info("Creating deduction {}", deduction.getId());
        return converter.toResponseDeduction(deduction);
    }

    @Override
    public void deleteDeduction(Long id) {
        Deduction deductionResponseDTO = deductionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Deduction not found by id: " + id));
        log.info("Deduction found by id {}", id);
        deductionRepository.delete(deductionResponseDTO);
        log.info("Deleting deduction {}",id);

    }

    @Override
    public DeductionResponseDTO updateDeduction(Long id, DeductionRequestDTO deductionDTO) {
        Deduction deduction = deductionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Deduction not found by id: " + id));
        log.info("Deduction found by id {}", id);

        converter.updateDeduction(deduction, deductionDTO);
        log.info("Updated deduction {}", deduction.getId());

        Deduction updatedDeduction = deductionRepository.save(deduction);
        log.info("Deduction saved");
        return converter.toResponseDeduction(updatedDeduction);
    }

    @Override
    public List<DeductionResponseDTO> getBetweenDates(LocalDate startDate, LocalDate endDate) {
        log.info("Getting all deductions between {} and {}", startDate, endDate);

        if (startDate == null || endDate == null || endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Invalid date range");
        }

        List<Deduction> deductions = deductionRepository.findByCreatedDateBetween(startDate, endDate);
        log.info("Deductions found between {} and {}", startDate, endDate);
        return converter.toResponseDeductionList(deductions);
    }

    @Override
    public List<DeductionResponseDTO> getByRate(DeductionRate deductionRate) {
        log.info("Getting all deductions by rate {}", deductionRate);
        List<Deduction> deductions = deductionRepository.findByRate(deductionRate);
        log.info("Deductions found by rate {}", deductionRate);

        return  converter.toResponseDeductionList(deductions);
    }
}
