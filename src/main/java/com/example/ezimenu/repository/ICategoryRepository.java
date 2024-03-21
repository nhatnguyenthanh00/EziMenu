package com.example.ezimenu.repository;

import com.example.ezimenu.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAllByEateryId(int eateryId);

    Category findById(int id);

    Category findCategoryByEatery_IdAndName(int eateryId, String name);

}
