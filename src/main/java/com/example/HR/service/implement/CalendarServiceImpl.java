package com.example.HR.service.implement;

import com.example.HR.converter.CalendarConverter;
import com.example.HR.dto.calendar.CalendarRequestDTO;
import com.example.HR.dto.calendar.CalendarResponseDTO;
import com.example.HR.entity.Calendar;
import com.example.HR.exception.NoIDException;
import com.example.HR.repository.CalendarRepository;
import com.example.HR.service.CalendarService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;
    private final CalendarConverter converter;

    @Override
    public CalendarResponseDTO addEvent(CalendarRequestDTO dto) {
        log.info("Adding new event");

        Calendar converted = converter.requestToEntity(dto);
        Calendar savedEvent = calendarRepository.save(converted);

        log.info("Event added successfully: {}", dto.getEventName());

        return converter.toResponseDTO(savedEvent);
    }

    @Override
    public List<CalendarResponseDTO> getAll() {
        log.info("Fetch all events");
        return converter.toResponseDTOList(calendarRepository.findAll());
    }

    @Override
    public CalendarResponseDTO updateEvent(Long id, CalendarRequestDTO dto) {
        log.info("Updates event with given id: {}", id);
        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(() -> new NoIDException("There is no event by id: " + id));

        converter.update(dto,calendar);
        log.info("Event updated successfully: {}",dto.getEventName());

        Calendar saved = calendarRepository.save(calendar);

        return converter.toResponseDTO(calendar);
    }

    @Override
    public CalendarResponseDTO getById(Long id) {
        return null;
    }

    @Override
    public CalendarResponseDTO getByDate(LocalDate date) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
