package com.example.HR.entity;

import com.example.HR.exception.InvalidDateRangeException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "calendar")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_name",nullable = false,length = 25)
    private String eventName;

    @Column(name = "start_time",nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time",nullable = false)
    private LocalTime endTime;

    @Column(name = "event_date",nullable = false)
    private LocalDate eventDate;

    @Column(name = "location", length = 150)
    private String location;

    @Column(name = "description", length = 500)
    private String description;

    @PrePersist
    @PreUpdate
    private void validateDates(){
        if(endTime.isBefore(startTime)){
            throw new InvalidDateRangeException("Invalid Date Range");
        }
    }
}
