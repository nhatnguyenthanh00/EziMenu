package com.example.ezimenu.dto;

import com.example.ezimenu.entity.Category;
import com.example.ezimenu.entity.Dish;
import com.example.ezimenu.service.serviceimpl.CategoryService;
import com.example.ezimenu.service.serviceimpl.DishService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DishDto {

    private int id;
    @NotNull
    private Integer categoryId;
    @NotNull
    private String name;
    @NotNull
    private Integer price;
    @NotNull
    private boolean status = true;

}
