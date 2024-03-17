package com.example.ezimenu.service;

import com.example.ezimenu.entity.Dish;

import java.util.List;

public interface IDishService {

    List<Dish> findAllByEateryId(int eateryId);

    List<Dish> findAllByCategoryId(int categoryId);

    Dish findById(int id);

    Dish saveDish(Dish dish);
}
