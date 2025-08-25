package com.example.HR.dto.tool;

import com.example.HR.validation.Create;
import com.example.HR.validation.Update;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Unique identifier", example = "1")
    private Long id;

    @NotBlank(message = "Tool name is mandatory",groups = {Create.class})
    @Size(max = 25, message = "Tool name must not exceed 25 characters",groups = {Create.class})
    private String name;

    @NotEmpty(message = "At least one skill is required",groups = {Create.class})
    private List<String> skills;
}
