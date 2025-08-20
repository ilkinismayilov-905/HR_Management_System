package com.example.HR.service;


import com.example.HR.dto.experience.ExperienceRequestDTO;
import com.example.HR.dto.experience.ExperienceResponseDTO;

import java.util.List;

public interface ExperienceService {

    List<ExperienceResponseDTO> getAllExperiences();
    ExperienceResponseDTO getExperienceById(Long id);
    ExperienceResponseDTO createExperience(ExperienceRequestDTO requestDTO);
    ExperienceResponseDTO updateExperience(Long id, ExperienceRequestDTO requestDTO);
    void deleteExperience(Long id);
}
