package com.example.auction.repositories;

import com.example.auction.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findByStatus(String status);
}
