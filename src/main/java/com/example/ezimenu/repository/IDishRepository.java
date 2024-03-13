package com.example.ezimenu.repository;

import com.example.ezimenu.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDishRepository extends JpaRepository<Dish, Integer> {
}
