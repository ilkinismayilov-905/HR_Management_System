package com.example.HR.service.implement;

import com.example.HR.converter.ExperienceConverter;
import com.example.HR.dto.experience.ExperienceRequestDTO;
import com.example.HR.dto.experience.ExperienceResponseDTO;
import com.example.HR.entity.employee.Experience;
import com.example.HR.exception.NoIDException;
import com.example.HR.exception.NotFoundException;
import com.example.HR.repository.ExperienceRepository;
import com.example.HR.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ExperienceConverter experienceConverter;

    @Override
    @Transactional(readOnly = true)
    public List<ExperienceResponseDTO> getAllExperiences() {
        List<Experience> list = experienceRepository.findAllExperiences();

        return experienceConverter.toResponseDTOList(list);
    }

    @Override
    @Transactional(readOnly = true)
    public ExperienceResponseDTO getExperienceById(Long id) {
        Experience experience = experienceRepository.findByIdExperiences(id)
                .orElseThrow(() -> new NoIDException("Experience not found by id: " + id));

        return experienceConverter.toResponseDTO(experience);
    }

    @Override
    public ExperienceResponseDTO createExperience(ExperienceRequestDTO requestDTO) {
        Experience experience = experienceConverter.toEntity(requestDTO);
        Experience savedExperience = experienceRepository.save(experience);
        Experience fetchedExperiences = experienceRepository.findByIdExperiences(savedExperience.getId())
                .orElseThrow(() -> new RuntimeException("Error fetching created experience"));

        return experienceConverter.toResponseDTO(fetchedExperiences);
    }

    @Override
    public ExperienceResponseDTO updateExperience(Long id, ExperienceRequestDTO requestDTO) {

        Experience existingExperience = experienceRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Experience not found with id: {}", id);
                    return new NotFoundException("Experience not found with id: " + id);
                });

        Experience updatedData = experienceConverter.toEntity(requestDTO);
        existingExperience.getTools().clear();
        existingExperience.getTools().addAll(updatedData.getTools());

        // Tools-da Experience referansını düzgün təyin et
        existingExperience.getTools().forEach(tool -> {
            tool.setExperience(existingExperience);
            tool.getSkills().forEach(skill -> skill.setTool(tool));
        });

        Experience savedExperience = experienceRepository.save(existingExperience);

        log.info("Experience updated successfully with id: {}", savedExperience.getId());

        // Response qaytarmaq üçün yenidən fetch edirik
        Experience fetchedExperience = experienceRepository.findByIdExperiences(savedExperience.getId())
                .orElseThrow(() -> new RuntimeException("Error fetching updated experience"));

        return experienceConverter.toResponseDTO(fetchedExperience);
    }

    @Override
    public void deleteExperience(Long id) {
        log.info("Deleting experience with id: {}", id);

        if (!experienceRepository.existsById(id)) {
            log.error("Experience not found with id: {}", id);
            throw new NotFoundException("Experience not found with id: " + id);
        }

        experienceRepository.deleteById(id);

        log.info("Experience deleted successfully with id: {}", id);
    }
}
