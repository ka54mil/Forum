package com.example.auction.controllers.helpers;

import com.example.auction.models.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;

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
}
