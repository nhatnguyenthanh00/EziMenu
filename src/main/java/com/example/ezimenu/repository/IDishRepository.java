package com.example.ezimenu.repository;

import com.example.ezimenu.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IDishRepository extends JpaRepository<Dish, Integer> {
    @Query("SELECT d FROM Dish d JOIN d.category c JOIN c.eatery e WHERE e.id = :eateryId")
    List<Dish> findAllByEateryId(@Param("eateryId") int eateryId);
}
