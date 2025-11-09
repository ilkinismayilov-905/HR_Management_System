package com.example.HR.service.implement;

import com.example.HR.converter.EducatoinInfoConverter;
import com.example.HR.dto.EducationInfoDTO;
import com.example.HR.entity.employee.EducationInformation;
import com.example.HR.exception.NoIDException;
import com.example.HR.exception.NotFoundException;
import com.example.HR.repository.EducationInfoRepository;
import com.example.HR.service.EducationInfoService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
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
       EducationInformation info = infoRepository.findById(id)
               .orElseThrow(() -> new NoIDException("Not found by id: " + id));
       log.info("Education information found by id: {}", id);

       infoRepository.deleteById(id);
    }

    @Override
    public EducationInfoDTO save(EducationInfoDTO dto) throws IOException {
        if(Integer.parseInt(dto.getStartDate()) > Integer.parseInt(dto.getEndDate())){
            throw new NotFoundException("Start year cannot be after End year");
        }
        EducationInformation convert = infoRepository.save(converter.dtoToEntity(dto));
        log.info("Education information saved: {}", dto.getId());
        return converter.entityToDto(convert);
    }

    @Override
    public Optional<EducationInfoDTO> getById(Long id) {
        EducationInformation info = infoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found by id: " + id));
        log.info("Education information found by id: {}", id);

        return Optional.ofNullable(converter.entityToDto(info));
    }

    @Override
    public EducationInfoDTO update(Long id, EducationInfoDTO updatedDto) {
        EducationInformation info = infoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("information not found by id: " + id));
        log.info("Education information found by id: {}", id);

        converter.update(updatedDto,info);
        log.info("Education information updated");

        EducationInformation savedInfo = infoRepository.save(info);
        log.info("Education information saved");

        return converter.entityToDto(savedInfo);
    }

    @Override
    public List<EducationInfoDTO> getAll() throws MalformedURLException {
        return converter.entityListToDtoList(infoRepository.findAll());
    }
}
