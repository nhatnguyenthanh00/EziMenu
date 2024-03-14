package com.example.ezimenu.entity;

import com.example.ezimenu.dto.DishDto;
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
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private double price;
    @Column(name = "status")
    private boolean status;

    public DishDto toDto(){
        DishDto dishDto = new DishDto();
        dishDto.setId(this.getId());
        dishDto.setCategoryId(this.getCategory().getId());
        dishDto.setName(this.getName());
        dishDto.setPrice(this.getPrice());
        dishDto.setStatus(this.isStatus());
        return dishDto;
    }

}
