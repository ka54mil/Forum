package com.example.auction.models;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "items")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 50)
    private String name;

    @DecimalMin("0")
    @DecimalMax("999999999")
    private BigDecimal price;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    private Date created;

    @Temporal(TemporalType.DATE)
    @Column(name="end_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @Size(min = 1, max = 20)
    private String status;

    @Valid
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="owner_id", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="payer_id")
    private User winner;

    @ManyToMany(fetch = FetchType.EAGER)
    @Size(min=1)
    @JoinTable(name = "items_categories",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

    public Item(String name, BigDecimal price, Date endDate, String status, User owner) {
        this.name = name;
        this.price = price;
        this.endDate = endDate;
        this.owner = owner;
        this.status = status;
    }

    public BigDecimal getMinBidPrice(){
        return price.add(price.divide(new BigDecimal(10),BigDecimal.ROUND_CEILING)).setScale(2, BigDecimal.ROUND_CEILING);
    }
    public enum Statuses{
        Wstrzymana,
        Aktywna,
        Zakonczona
    }
}
