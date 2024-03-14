package com.example.ezimenu.service;

import com.example.ezimenu.entity.Dish;

import java.util.List;

public interface IDishService {

    List<Dish> findAllByEateryId(int eateryId);
}
