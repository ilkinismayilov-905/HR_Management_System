package com.example.HR.dto.tool;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolResponseDTO {

    private Long id;

    @NotBlank(message = "Tool name is mandatory")
    @Size(max = 25, message = "Tool name must not exceed 25 characters")
    private String name;

    private List<String> skills;


}
