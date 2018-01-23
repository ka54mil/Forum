package com.example.auction.models;

import com.example.auction.validators.annotations.UniqueCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UniqueCategory
    @Size(min = 1, max = 50)
    private String name;

    private boolean active;


    @ManyToMany(mappedBy = "categories")
    private Set<Item> items;
}
