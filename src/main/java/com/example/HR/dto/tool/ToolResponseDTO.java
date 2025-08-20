package com.example.HR.dto.tool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToolResponseDTO {
    private Long id;
    private String name;
    private Set<String> skills;
}
