package com.example.ezimenu.service;

import com.example.ezimenu.entity.Notify;

import java.util.List;

public interface INotifyService {
    Notify findById(int id);
    boolean deleteByid(int id);

    Notify saveNotify(Notify notify);

    List<Notify> findAllByEateryId(int eateryId);
    List<Notify> findAllByTableDinnerId(int tableDinnerId);

    boolean deleteById(int id);
}
