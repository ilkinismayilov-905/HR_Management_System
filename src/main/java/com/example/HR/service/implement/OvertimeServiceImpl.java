package com.example.HR.service.implement;

import com.example.HR.converter.payroll.AddictionConverter;
import com.example.HR.converter.payroll.DeductionConverter;
import com.example.HR.converter.payroll.OvertimeConverter;
import com.example.HR.converter.payroll.PayrollConverter;
import com.example.HR.dto.payroll.overtime.OvertimeRequestDTO;
import com.example.HR.dto.payroll.overtime.OvertimeResponseDTO;
import com.example.HR.entity.payroll.Overtime;
import com.example.HR.exception.NotFoundException;
import com.example.HR.repository.payroll.OvertimeRepository;
import com.example.HR.service.OvertimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OvertimeServiceImpl implements OvertimeService {

    private final OvertimeRepository overtimeRepository;
    private final OvertimeConverter converter;


    @Override
    public List<OvertimeResponseDTO> getAllOvertimes() {
        log.info("Getting all overtimes");
        return converter.toResponseOvertimeList(overtimeRepository.findAll());
    }

    @Override
    public OvertimeResponseDTO getOvertimeById(Long id) {
        Overtime overtime = overtimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Overtime not found with id: " + id));
        log.info("Getting overtime by id: {}", overtime);
        return converter.toResponseOvertime(overtime);
    }

    @Override
    public OvertimeResponseDTO createOvertime(OvertimeRequestDTO overtimeDTO) {
        Overtime overtime = converter.toEntityOvertime(overtimeDTO);

        Overtime savedOvertime = overtimeRepository.save(overtime);
        log.info("Saving overtime by id: {}", savedOvertime.getId());
        return converter.toResponseOvertime(savedOvertime);
    }

    @Override
    public void deleteOvertime(Long id) {
        if (!overtimeRepository.existsById(id)) {
            throw new NotFoundException("Overtime not found with id: " + id);
        }
        overtimeRepository.deleteById(id);
        log.info("Deleting overtime by id: {}", id);
    }

    @Override
    public OvertimeResponseDTO updateOvertime(Long id, OvertimeRequestDTO overtimeDTO) {
        Overtime overtime = overtimeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Overtime not found with id: " + id));

        converter.updateOvertime(overtime, overtimeDTO);
        Overtime updatedOvertime = overtimeRepository.save(overtime);
        log.info("Updating overtime by id: {}", id);
        return converter.toResponseOvertime(updatedOvertime);
    }

    @Override
    public List<OvertimeResponseDTO> getBetweenDates(LocalDate startDate, LocalDate endDate) {
        log.info("View events between dates: {} and {}", startDate, endDate);

        if (startDate == null || endDate == null || endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Invalid date range");
        }

        List<Overtime> events = overtimeRepository.findByCreatedDateBetween(startDate, endDate);
        log.info("Getting all additions between dates: {} and {}", startDate, endDate);
        return converter.toResponseOvertimeList(events);
    }
}
