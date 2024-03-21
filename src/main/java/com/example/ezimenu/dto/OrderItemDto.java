package com.example.ezimenu.dto;

import com.example.ezimenu.entity.Dish;
import com.example.ezimenu.entity.Order;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNullFields;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemDto {

    private int id;
    @NonNull
    private int orderId;
    @NonNull
    private int dishId;
    @NonNull
    private int quantity = 0;

    private boolean status = false;
}
