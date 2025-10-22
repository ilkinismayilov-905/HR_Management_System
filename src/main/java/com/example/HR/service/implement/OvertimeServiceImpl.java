package com.example.HR.service.implement;

import com.example.HR.converter.PayrollConverter;
import com.example.HR.dto.payroll.OvertimeRequestDTO;
import com.example.HR.dto.payroll.OvertimeResponseDTO;
import com.example.HR.entity.payroll.Overtime;
import com.example.HR.exception.NotFoundException;
import com.example.HR.repository.payroll.OvertimeRepository;
import com.example.HR.service.OvertimeService;
import com.example.HR.service.PayrollService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OvertimeServiceImpl implements OvertimeService {

    private final OvertimeRepository overtimeRepository;
    private final PayrollConverter converter;

    @Override
    public List<OvertimeResponseDTO> getAllOvertimes() {
        return converter.toResponseOvertimeList(overtimeRepository.findAll());
    }

    @Override
    public OvertimeResponseDTO getOvertimeById(Long id) {
        Overtime overtime = overtimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Overtime not found with id: " + id));
        return converter.toResponseOvertime(overtime);
    }

    @Override
    public OvertimeResponseDTO createOvertime(OvertimeRequestDTO overtimeDTO) {
        Overtime overtime = converter.toEntityOvertime(overtimeDTO);

        Overtime savedOvertime = overtimeRepository.save(overtime);
        return converter.toResponseOvertime(savedOvertime);
    }

    @Override
    public void deleteOvertime(Long id) {
        if (!overtimeRepository.existsById(id)) {
            throw new NotFoundException("Overtime not found with id: " + id);
        }
        overtimeRepository.deleteById(id);
    }

    @Override
    public OvertimeResponseDTO updateOvertime(Long id, OvertimeRequestDTO overtimeDTO) {
        Overtime overtime = overtimeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Overtime not found with id: " + id));

        converter.updateOvertime(overtime, overtimeDTO);
        Overtime updatedOvertime = overtimeRepository.save(overtime);
        return converter.toResponseOvertime(updatedOvertime);
    }
}
