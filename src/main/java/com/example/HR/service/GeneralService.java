package com.example.HR.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

public interface GeneralService<T,K> {
    void deleteById(K id);
    T save(T entity) throws IOException;
    Optional<T> getById(K id);
    List<T> getAll() throws MalformedURLException;


}
