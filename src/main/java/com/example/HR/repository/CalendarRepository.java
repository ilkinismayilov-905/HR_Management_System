package com.example.HR.repository;

import com.example.HR.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar,Long> {
    Optional<Calendar> findByEventDate(LocalDate date);
    List<Calendar> findByEventDateBetween(LocalDate startDate, LocalDate endDate);
}
