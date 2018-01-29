package com.example.auction.repositories;

import com.example.auction.models.Category;
import com.example.auction.models.Item;
import com.example.auction.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

    Item findByStatus(String status);
    @Query("SELECT i FROM Item i " +
            "WHERE " +
            "(" +
            ":name is null OR :name = '' OR "+
            "upper(i.name) LIKE upper(:name) " +
            ") " +
            "AND " +
            "(:min is null OR :min <= i.price) " +
            "AND (:max is null OR :max >= i.price)" +
            "AND" +
            "(" +
            ":status is null OR :status = '' OR "+
            "i.status = :status)" +
            "AND" +
            "("+
            "i.endDate >= current_date)")
    List<Item> findAllByStatusAndEndDateGreaterThanEqualOrderByEndDateAsc(
            @Param("name") String name,
            @Param("min") BigDecimal priceMin,
            @Param("max") BigDecimal priceMax,
//            Pageable pageable,
            @Param("status") String status
    );
//    Page<Item> findAllByStatusAndEndDateGreaterThanEqualOrderByEndDateAsc(Specification specification, Pageable pageable, String status, Date endDate);

    List<Item> findItemsByStatusAndEndDateLessThanOrderByEndDateAsc(String status, Date endDate);
//    Page<Item> findItemsByStatusAndNameLikeAndPriceGreaterThanEqualAndPriceLessThanEqualAndCategoriesIn(String status,String name, BigDecimal priceMin, BigDecimal priceMax, List<Long> category_ids, Pageable pageable);
    Page<Item> findItemsByOwner(Pageable pageable, User user);
    List<Item> findItemsByOwner(User user);

}
