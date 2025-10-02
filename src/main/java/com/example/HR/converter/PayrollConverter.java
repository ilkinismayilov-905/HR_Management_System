package com.example.HR.converter;

import com.example.HR.dto.payroll.PayrollItemDTO;
import com.example.HR.entity.payroll.PayrollItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PayrollConverter {
    public PayrollItemDTO convertToDTO(PayrollItem item) {
        return PayrollItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .category(item.getCategory())
                .amount(item.getAmount())
                .additionDate(item.getAdditionDate())
                .build();
    }

    public List<PayrollItemDTO> toDTOList(List<PayrollItem> list){
        return list.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}
