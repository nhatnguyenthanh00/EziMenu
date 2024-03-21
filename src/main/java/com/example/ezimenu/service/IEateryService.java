package com.example.ezimenu.service;

import com.example.ezimenu.entity.Eatery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IEateryService {
    Eatery saveEatery(Eatery eatery);

    List<Eatery> findAllByUserId(int userid);

    Eatery findById(int id);

    Eatery findByTableDinnerId(int tableDinner);

}
