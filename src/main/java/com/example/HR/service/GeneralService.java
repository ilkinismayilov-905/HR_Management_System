package com.example.HR.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

public interface GeneralService<T,K> {
    void deleteById(K id);
    T save(T dto) throws IOException;
    Optional<T> getById(K id);
    T update(K id,T updatedDto);
    List<T> getAll() throws MalformedURLException;


}
