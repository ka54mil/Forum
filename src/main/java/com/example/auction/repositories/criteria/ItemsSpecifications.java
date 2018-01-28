package com.example.auction.repositories.criteria;

import com.example.auction.models.Category;
import com.example.auction.models.Item;
import com.example.auction.models.Item_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

public class ItemsSpecifications {
    public static Specification<Item> findByName(final String name) {
        return (root, query, cb) -> {

            if(!StringUtils.isEmpty(name)){
                String phraseLike = "%"+name.toUpperCase() +"%";
                return  cb.and(
                                cb.like(cb.upper(root.get(Item_.name)), phraseLike)
                        );
            }
            return null;
        };
    }

    public static Specification<Item> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, cb) -> {
            if(minPrice != null && maxPrice != null){
                return cb.between(root.get(Item_.price), minPrice, maxPrice);
            }else if(minPrice != null){
                return cb.greaterThanOrEqualTo(root.get(Item_.price), minPrice);
            }else if(maxPrice != null) {
                return cb.lessThanOrEqualTo(root.get(Item_.price), maxPrice);
            }
            return null;
        };
    }

//    public static Specification<Item> findByCategories(List<Category> categories) {
//        return (root, query, cb) -> {
//            if(categories != null && !categories.isEmpty()){
//                return cb.and(cb.iroot.get(Item_.categories), categories);
//            }
//            return null;
//        };
//    }
}
