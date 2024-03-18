package com.example.ezimenu.dto;

import com.example.ezimenu.entity.Category;
import com.example.ezimenu.entity.Dish;
import com.example.ezimenu.service.serviceimpl.CategoryService;
import com.example.ezimenu.service.serviceimpl.DishService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DishDto {

    private int id;
    private int categoryId;
    private String name;
    private int price;
    private boolean status;

}
