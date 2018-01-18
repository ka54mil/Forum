package com.example.auction.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "items")
@Getter @Setter
@NoArgsConstructor
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
    private Date created;

    @Future
    @Temporal(TemporalType.DATE)
    @Column(name="end_date")
    private Date endDate;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id", nullable = false)
    private User owner;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="payer_id", nullable = false)
    private User winner;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id", nullable = false)
    private Category category;

    @Size(min = 1, max = 20)
    private String status;

    public Item(String name, BigDecimal price, Date endDate, User owner, String status) {
        this.name = name;
        this.price = price;
        this.endDate = endDate;
        this.owner = owner;
        this.status = status;
    }
}