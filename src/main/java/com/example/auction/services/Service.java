package com.example.auction.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface Service<T> {

    T getById(Long id);
    Page<T> getAll(Pageable pageable);
    T save(T t);
    void delete(Long id);

}
