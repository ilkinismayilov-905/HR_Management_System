package com.example.HR.dto.tool;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolRequestDTO {

    @NotBlank(message = "Tool name is mandatory")
    @Size(max = 25, message = "Tool name must not exceed 25 characters")
    private String name;

    @NotEmpty(message = "At least one skill is required")
    private List<String> skills;
}
