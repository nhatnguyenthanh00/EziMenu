package com.example.ezimenu.service.transer;

import com.example.ezimenu.dto.*;
import com.example.ezimenu.entity.*;
import com.example.ezimenu.service.serviceimpl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapperService {
    @Autowired
    CategoryService categoryService;
    @Autowired
    EateryService eateryService;
    @Autowired
    TableDinnerService tableDinnerService;
    @Autowired
    OrderService orderService;
    @Autowired
    DishService dishService;
    public Dish toDish(DishDto dishDto){
        Dish dish = new Dish();
        dish.setId(dishDto.getId());
        dish.setCategory(categoryService.findById(dishDto.getCategoryId()));
        dish.setName(dishDto.getName());
        dish.setPrice(dishDto.getPrice());
        dish.setStatus(dishDto.isStatus());
        return dish;
    }

    public TableDinner toTableDinner(TableDinnerDto tableDinnerDto){
        TableDinner tableDinner = new TableDinner();
        tableDinner.setId(tableDinnerDto.getId());
        tableDinner.setEatery(eateryService.findById(tableDinnerDto.getEateryId()));
        tableDinner.setDescription(tableDinnerDto.getDescription());
        tableDinner.setStatus(tableDinnerDto.isStatus());
        return tableDinner;
    }

    public Order toOrder(OrderDto orderDto){
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setTableDinner(tableDinnerService.findById(orderDto.getTableDinnerId()));
        order.setDescription(orderDto.getDescription());
        order.setStatus(orderDto.getStatus());
        order.setTotalPrice(orderDto.getTotalPrice());
        return order;
    }

    public OrderItem toOrderItem(OrderItemDto orderItemDto){
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemDto.getId());
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setStatus(orderItemDto.isStatus());
        orderItem.setOrder(orderService.findById(orderItemDto.getOrderId()));
        orderItem.setDish(dishService.findById(orderItemDto.getDishId()));
        return orderItem;
    }

    public Notify toNotify(NotifyDto notifyDto){
        Notify notify = new Notify();
        notify.setType(notifyDto.getType());
        notify.setDescription(notifyDto.getDescription());
        notify.setTableDinner(tableDinnerService.findById(notifyDto.getTableDinnerId()));
        return notify;
    }
}
