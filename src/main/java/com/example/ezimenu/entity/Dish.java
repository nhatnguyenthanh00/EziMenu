package com.example.ezimenu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dish")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "eatery_id")
    private Eatery eatery;
    @Column(name = "category_id")
    private int categoryId;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private double price;

}
