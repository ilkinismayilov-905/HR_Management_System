package com.example.HR.record;

import java.time.LocalDate;
public record DailyAttendanceDto(LocalDate date, Long presentCount, Long totalCount) {}


