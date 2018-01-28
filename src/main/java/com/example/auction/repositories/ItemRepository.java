package com.example.auction.repositories;

import com.example.auction.models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

    Item findByStatus(String status);
//    @Query("SELECT i FROM Item i WHERE " +
//            "(" +
//            ":name is null OR :name = '' OR "+
//            "upper(i.name) LIKE upper(:name) " +
//            ") " +
//            "AND " +
//            "(:min is null OR :min <= i.price) " +
//            "AND (:max is null OR :max >= i.price)")
//    @Param("name") String p, @Param("min") BigDecimal priceMin, @Param("max") BigDecimal priceMax,
    Page<Item> findAllByStatusAndEndDateGreaterThanEqualOrderByEndDateAsc(Specification specification, Pageable pageable, String status, Date endDate);
    List<Item> findItemsByStatusAndEndDateLessThanOrderByEndDateAsc(String status, Date endDate);

}
