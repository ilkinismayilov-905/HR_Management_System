package com.example.HR.dto.experience;

import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienceResponseDTO {
    private Long id;
    private Map<String, Set<String>> experiences;
}
