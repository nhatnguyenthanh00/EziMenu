package com.example.ezimenu.dto;

import com.example.ezimenu.entity.Dish;
import com.example.ezimenu.entity.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemDto {

    private int id;

    private int orderId;

    private int dishId;

    private int quantity = 0;

    private boolean status = false;
}
