package com.example.ezimenu.service;

import com.example.ezimenu.entity.Eatery;

import java.util.List;

public interface IEateryService {
    Eatery saveEatery(Eatery eatery);

    List<Eatery> findAllByUserId(int userid);

    Eatery findById(int id);
}
