package com.example.HR.dto.calendar;

import com.example.HR.validation.Create;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarRequestDTO {

    @Schema(description = "Unique identifier", example = "1")
    private Long id;

    @NotBlank(message = "eventName is required",groups = Create.class)
    @Size(min = 3,max = 25,message = "eventName must be between 3 and 25 characters")
    private String eventName;

    @NotNull(message = "Event date is required",groups = Create.class)
    @FutureOrPresent(message = "Event date cannot be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate eventDate;

    @NotNull(message = "Start time is required",groups = Create.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull(message = "End time is required",groups = Create.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;

    @NotNull(message = "Location is required",groups = Create.class)
    @Size(max = 150, message = "Event location must not exceed 150 characters")
    private String location;

    @NotNull(message = "Description is required",groups = Create.class)
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;



}
