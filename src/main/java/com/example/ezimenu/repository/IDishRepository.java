package com.example.ezimenu.repository;

import com.example.ezimenu.entity.Category;
import com.example.ezimenu.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IDishRepository extends JpaRepository<Dish, Integer> {
    @Query("SELECT d FROM Dish d JOIN d.category c JOIN c.eatery e WHERE e.id = :eateryId")
    List<Dish> findAllByEateryId(@Param("eateryId") int eateryId);

    @Query("SELECT d FROM Dish d JOIN d.category c  WHERE c.id = :categoryId")
    List<Dish> findAllByCategoryId(@Param("categoryId") int categoryId);

    Dish findById(int id);

    Dish findDishByCategoryAndNameAndPrice(Category category, String name , int price);
}
