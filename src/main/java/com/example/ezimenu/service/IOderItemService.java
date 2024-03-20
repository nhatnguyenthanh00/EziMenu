package com.example.ezimenu.service;

import com.example.ezimenu.entity.OrderItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOderItemService {
    OrderItem findById(int id);

    List<OrderItem> findAllByOrderId(int orderId);

    OrderItem findByOrderIdAndDishId(int orderId, int dishId);

    OrderItem saveOrderItem(OrderItem orderItem);

    boolean deleteById(int id);
}
