package com.example.HR.converter;

import com.example.HR.dto.EducationInfoDTO;
import com.example.HR.entity.employee.EducationInformation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EducatoinInfoConverter extends Convert<EducationInfoDTO, EducationInformation>{

    @Override
    public EducationInformation dtoToEntity(EducationInfoDTO dto) {
        EducationInformation entity = new EducationInformation();

        entity.setId(dto.getId());
        entity.setUniversityName(dto.getUniversityName());
        entity.setMajor(dto.getMajor());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());

        return entity;
    }

    public List<EducationInformation> dtoListToEntityList(List<EducationInfoDTO> list){
        return list.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public EducationInfoDTO entityToDto(EducationInformation entity) {
        EducationInfoDTO dto = new EducationInfoDTO();

        dto.setId(entity.getId());
        dto.setUniversityName(entity.getUniversityName());
        dto.setMajor(entity.getMajor());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());

        return dto;
    }

    public List<EducationInfoDTO> entityListToDtoList(List<EducationInformation> list){
        return list.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public void update(EducationInfoDTO dto, EducationInformation entity) {
        if (dto == null || entity == null) {
            return;
        }
        if (dto.getId() != null) entity.setId(dto.getId());
        if (dto.getUniversityName() != null) entity.setUniversityName(dto.getUniversityName());
        if (dto.getMajor() != null) entity.setMajor(dto.getMajor());
        if (dto.getStartDate() != null) entity.setStartDate(dto.getStartDate());
        if (dto.getEndDate() != null) entity.setEndDate(dto.getEndDate());
    }
}
