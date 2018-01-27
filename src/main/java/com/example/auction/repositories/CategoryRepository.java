package com.example.auction.repositories;

import com.example.auction.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByNameIgnoreCaseAndIdNot(String name, Long id);
    Category findByNameIgnoreCase(String name);
    Page<Category> findAllByActive(boolean active, Pageable pageable);
    List<Category> findAllByActive(boolean active);
}
