package com.example.auction.controllers.helpers;

import com.example.auction.models.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter @Setter
public class SearchFilter {

    private String name;

    @DecimalMin("0")
    private BigDecimal minPrice;

    @DecimalMin("0")
    private BigDecimal maxPrice;

    private List<Category> categories;


    public boolean isEmpty(){
        return StringUtils.isEmpty(name) && maxPrice == null && minPrice == null && categories == null;
    }

    public void clear(){
        this.name = "";
        this.minPrice = null;
        this.maxPrice = null;
        this.categories = null;
    }

    public String getNameLIKE(){
        if(StringUtils.isEmpty(name)) {
            return null;
        }else{
            return "%"+name+"%";
        }
    }

    public String getCategoriesIN(){
        if(categories == null || categories.isEmpty()) {
            return null;
        }else{
            StringBuilder sb = new StringBuilder();
            for (Category category : categories){
                sb.append(category.getId()).append(',');
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            return "("+sb.toString()+")";
        }
    }
    public List<Long> getCategoriesIdsIN(){
        if(categories == null || categories.isEmpty()) {
            return null;
        }else{
            List<Long> list = new ArrayList<>();
            for (Category category : categories){
                list.add(category.getId());
            }

            return list;
        }
    }
}
