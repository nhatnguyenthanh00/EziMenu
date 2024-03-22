package com.example.ezimenu.repository;

import com.example.ezimenu.entity.Category;
import com.example.ezimenu.entity.TableDinner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITableDinnerRepository extends JpaRepository<TableDinner, Integer> {
    List<TableDinner> findAllByEateryId(int eateryId);

    TableDinner findById(int id);

    TableDinner findTableDinnerByEatery_IdAndAndDescription(int eateryId, String description);
}
