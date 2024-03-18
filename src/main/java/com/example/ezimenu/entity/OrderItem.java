package com.example.ezimenu.entity;

import com.example.ezimenu.dto.OrderItemDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @Column(name = "quantity")
    private int quantity = 0;

    @Column(name = "status")
    private boolean status = false;

    public OrderItemDto toDto(){
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(id);
        orderItemDto.setOrderId(order.getId());
        orderItemDto.setDishId(dish.getId());
        orderItemDto.setQuantity(quantity);
        orderItemDto.setStatus(status);
        return orderItemDto;
    }
}
