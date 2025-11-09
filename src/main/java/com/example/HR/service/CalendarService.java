package com.example.HR.service;

import com.example.HR.dto.calendar.CalendarRequestDTO;
import com.example.HR.dto.calendar.CalendarResponseDTO;
import com.example.HR.repository.CalendarRepository;

import java.time.LocalDate;
import java.util.List;

public interface CalendarService {
    CalendarResponseDTO addEvent(CalendarRequestDTO dto);
    List<CalendarResponseDTO> getAll();
    CalendarResponseDTO updateEvent(Long id,CalendarRequestDTO dto);
    CalendarResponseDTO getById(Long id);
    CalendarResponseDTO getByDate(LocalDate date);
    List<CalendarResponseDTO> getBetweenDates(LocalDate startDate, LocalDate endDate);
    void deleteById(Long id);
    List<CalendarResponseDTO> getUpcomingEvents();
}
