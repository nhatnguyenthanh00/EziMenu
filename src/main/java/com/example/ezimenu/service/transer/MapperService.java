package com.example.ezimenu.service.transer;

import com.example.ezimenu.dto.DishDto;
import com.example.ezimenu.dto.OrderDto;
import com.example.ezimenu.dto.TableDinnerDto;
import com.example.ezimenu.entity.Dish;
import com.example.ezimenu.entity.Order;
import com.example.ezimenu.entity.TableDinner;
import com.example.ezimenu.service.serviceimpl.CategoryService;
import com.example.ezimenu.service.serviceimpl.DishService;
import com.example.ezimenu.service.serviceimpl.EateryService;
import com.example.ezimenu.service.serviceimpl.TableDinnerService;
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
}
