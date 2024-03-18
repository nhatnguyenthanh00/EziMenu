package com.example.ezimenu.service;

import com.example.ezimenu.entity.Order;

import java.util.List;

public interface IOrderService {
    Order findById(int id);

    List<Order> findAllByEateryId(int eateryId);

    List<Order> findAllByTableDinnerId(int tableDinnerId);

    Order saveOrder(Order order);
}
