package com.example.HR.service.implement;

import com.example.HR.converter.payroll.AddictionConverter;
import com.example.HR.converter.payroll.PayrollConverter;
import com.example.HR.dto.payroll.addiction.AdditionRequestDTO;
import com.example.HR.dto.payroll.addiction.AdditionResponseDTO;
import com.example.HR.entity.payroll.Addition;
import com.example.HR.enums.payroll.AdditionCategory;
import com.example.HR.exception.NotFoundException;
import com.example.HR.repository.payroll.AdditionRepository;
import com.example.HR.service.AdditionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdditoinServiceImpl implements AdditionService {

    private final AdditionRepository additionRepository;
    private final AddictionConverter converter;

    @Override
    @Transactional(readOnly = true)
    public List<AdditionResponseDTO> getAllAdditions() {
        log.info("Getting all additions");
        return converter.toResponseAdditionList(additionRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public AdditionResponseDTO getAdditionById(Long id) {
        Addition addition = additionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Addition not found: " + id));
        log.info("Addition found by ID: " +id);
        return converter.toResponseAddition(addition);
    }

    @Override
    public AdditionResponseDTO createAddition(AdditionRequestDTO dto) {
        Addition addition = converter.toEntityAddition(dto);
        Addition saved = additionRepository.save(addition);
        log.info("Addition created by ID: " +saved.getId());
        return converter.toResponseAddition((saved));
    }

    @Override
    public AdditionResponseDTO updateAddition(Long id, AdditionRequestDTO dto) {
        Addition existing = additionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Addition not found: " + id));
        converter.updateAddition(existing, dto);
        Addition saved = additionRepository.save(existing);
        log.info("Addition updated by ID: " +saved.getId());
        return converter.toResponseAddition(saved);
    }

    @Override
    public List<AdditionResponseDTO> getAdditionsByCategory(AdditionCategory category) {
        List<Addition> addition = additionRepository.findByCategory(category);
        log.info("Getting all additions by category: " +category);
        return converter.toResponseAdditionList(addition);
    }

    @Override
    public void deleteAddition(Long id) {
        if (!additionRepository.existsById(id)) {
            throw new IllegalArgumentException("Addition not found: " + id);
        }
        additionRepository.deleteById(id);
        log.info("Deleted addition by ID: " +id);
    }

    @Override
    public List<AdditionResponseDTO> getBetweenDates(LocalDate startDate, LocalDate endDate) {
        log.info("View events between dates: {} and {}", startDate, endDate);

        if (startDate == null || endDate == null || endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Invalid date range");
        }

        List<Addition> events = additionRepository.findByAdditionDateBetween(startDate, endDate);
        log.info("Getting all additions between dates: {} and {}", startDate, endDate);
        return converter.toResponseAdditionList(events);
    }

}
