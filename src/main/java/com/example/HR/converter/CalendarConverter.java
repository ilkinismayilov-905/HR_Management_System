package com.example.HR.converter;

import com.example.HR.dto.calendar.CalendarRequestDTO;
import com.example.HR.dto.calendar.CalendarResponseDTO;
import com.example.HR.entity.Calendar;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CalendarConverter {
    public CalendarResponseDTO toResponseDTO(Calendar calendar){
        if(calendar == null){
            return null;
        }

        return CalendarResponseDTO.builder()
                .eventName(calendar.getEventName())
                .startTime(calendar.getStartTime())
                .endTime(calendar.getEndTime())
                .eventDate(calendar.getEventDate())
                .description(calendar.getDescription())
                .eventLocation(calendar.getLocation())
                .upcoming(calendar.isUpcoming())
                .build();
    }

    public List<CalendarResponseDTO> toResponseDTOList(List<Calendar> list){

        return list.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Calendar requestToEntity(CalendarRequestDTO dto){
        if (dto == null) return null;

        return Calendar.builder()
                .id(dto.getId())
                .eventName(dto.getEventName())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .eventDate(dto.getEventDate())
                .location(dto.getLocation())
                .description(dto.getDescription())
                .build();
    }

    public List<Calendar> requestToEntityList(List<CalendarRequestDTO> list){

        return list.stream()
                .map(this::requestToEntity)
                .collect(Collectors.toList());
    }

    public void update(CalendarRequestDTO dto, Calendar entity){
        if(dto == null || entity == null){
            return;
        }

        if(dto.getId() != null) entity.setId(dto.getId());
        if(dto.getEventName() != null) entity.setEventName(dto.getEventName());
        if(dto.getEventDate() != null) entity.setEventDate(dto.getEventDate());
        if(dto.getStartTime() != null) entity.setStartTime(dto.getStartTime());
        if(dto.getEndTime() != null) entity.setEndTime(dto.getEndTime());
        if(dto.getLocation() != null) entity.setLocation(dto.getLocation());
        if(dto.getDescription() != null) entity.setDescription(dto.getDescription());

    }
}
