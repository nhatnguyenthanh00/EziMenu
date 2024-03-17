package com.example.ezimenu.service.transer;

import com.example.ezimenu.dto.DishDto;
import com.example.ezimenu.entity.Dish;
import com.example.ezimenu.service.serviceimpl.CategoryService;
import com.example.ezimenu.service.serviceimpl.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapperService {
    @Autowired
    CategoryService categoryService;
    @Autowired
    DishService dishService;
    public Dish toDish(DishDto dishDto){
        Dish dish = new Dish();
        dish.setId(dishDto.getId());
        dish.setCategory(categoryService.findById(dishDto.getCategoryId()));
        dish.setName(dishDto.getName());
        dish.setPrice(dishDto.getPrice());
        dish.setStatus(dishDto.isStatus());
        return dish;
    }
}
