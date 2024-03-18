package com.example.ezimenu.service;

import com.example.ezimenu.entity.TableDinner;

import java.util.List;

public interface ITableDinnerService {
    TableDinner saveTableDinner(TableDinner tableDinner);

    List<TableDinner> findAllByEateryId(int eateryId);

    TableDinner findById(int id);

    boolean deleteById(int id);
}
