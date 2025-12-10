package com.example.HR.repository;

import com.example.HR.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.date = :date AND a.present = true")
    long countPresent(LocalDate date);
}
