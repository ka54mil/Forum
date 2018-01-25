package com.example.auction.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Service<T> {

    T getById(Long id);
    Page<T> getAll(Pageable pageable);
    List<T> getAll();
    T save(T t);
    void delete(Long id);

}
