package com.example.HR.dto.experience;

import lombok.*;

import jakarta.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceRequestDTO {

    @NotNull(message = "Tools and skills are required")
    private Map<String, Set<String>> experiences;
}
