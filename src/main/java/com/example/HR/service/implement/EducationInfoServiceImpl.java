package com.example.HR.service.implement;

import com.example.HR.converter.EducatoinInfoConverter;
import com.example.HR.dto.EducationInfoDTO;
import com.example.HR.entity.employee.EducationInformation;
import com.example.HR.exception.NotFoundException;
import com.example.HR.repository.EducationInfoRepository;
import com.example.HR.service.EducationInfoService;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EducationInfoServiceImpl implements EducationInfoService {

    private final EducationInfoRepository infoRepository;
    private final EducatoinInfoConverter converter;

    @Autowired
    public EducationInfoServiceImpl(EducationInfoRepository infoRepository, EducatoinInfoConverter converter) {
        this.infoRepository = infoRepository;
        this.converter = converter;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public EducationInfoDTO save(EducationInfoDTO dto) throws IOException {
        if(Integer.parseInt(dto.getStartDate()) > Integer.parseInt(dto.getEndDate())){
            throw new NotFoundException("Start year cannot be after End year");
        }
        EducationInformation convert = infoRepository.save(converter.dtoToEntity(dto));
        return converter.entityToDto(convert);
    }

    @Override
    public Optional<EducationInfoDTO> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public EducationInfoDTO update(Long id, EducationInfoDTO updatedDto) {
        return null;
    }

    @Override
    public List<EducationInfoDTO> getAll() throws MalformedURLException {
        List<EducationInfoDTO> list = converter.entityListToDtoList(infoRepository.findAll());
        return list;
    }
}
