package com.example.ezimenu.service;

import com.example.ezimenu.entity.Category;

import java.util.List;

public interface ICategoryService {
    Category saveCategory(Category category);

    List<Category> findAllByEateryId(int eateryId);

    Category findById(int id);

    boolean deleteById(int id);

    Category findCategoryByEateryIdAndName(int eateryId, String name);

}
