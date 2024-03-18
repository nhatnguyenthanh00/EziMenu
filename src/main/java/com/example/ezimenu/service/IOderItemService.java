package com.example.ezimenu.service;

import com.example.ezimenu.entity.OrderItem;

import java.util.List;

public interface IOderItemService {
    OrderItem findById(int id);

    List<OrderItem> findAllByOrderId(int orderId);
}
