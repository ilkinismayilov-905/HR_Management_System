package com.example.HR.converter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Convert<D, E> {
    public abstract E dtoToEntity(D dto);
    public abstract D entityToDto(E entity);

    public List<E> dtoListToEntityList(List<D> dtoList) {
        if (dtoList == null) {
            return Collections.emptyList();
        }
        return dtoList.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

    public List<D> entityListToDtoList(List<E> entityList) {
        if (entityList == null) {
            return Collections.emptyList();
        }
        return entityList.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
