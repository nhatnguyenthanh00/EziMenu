package com.example.ezimenu.controller;

import com.example.ezimenu.dto.DishDto;
import com.example.ezimenu.dto.OrderDto;
import com.example.ezimenu.dto.OrderItemDto;
import com.example.ezimenu.entity.*;
import com.example.ezimenu.service.serviceimpl.*;
import com.example.ezimenu.service.transer.MapperService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    EateryService eateryService;
    @Autowired
    TableDinnerService tableDinnerService;
    @Autowired
    MapperService mapperService;
    @GetMapping(value = "/eatery/{id}/orders")
    public ResponseEntity<?> getAllOrderByTable(@PathVariable int id){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Eatery eatery = eateryService.findById(id);

        if(eatery==null || eatery.getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }

        List<Order> orderList = orderService.findAllByEateryId(id);
        List<OrderDto> orderDtoList = new ArrayList<>();
        for(Order order : orderList){
            orderDtoList.add(order.toDto());
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderDtoList);
    }

    @GetMapping(value = "/table/{id}/orders")
    public ResponseEntity<?> getAllOrderByTableId(@PathVariable int id){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        TableDinner tableDinner = tableDinnerService.findById(id);
        if(tableDinner == null || tableDinner.getEatery().getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }
        List<Order> orderList = orderService.findAllByTableDinnerId(id);
        List<OrderDto> orderDtoList = new ArrayList<>();
        for(Order order : orderList){
            orderDtoList.add(order.toDto());
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderDtoList);
    }

    @GetMapping(value = "/order/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable int id){
        HttpSession session = request.getSession();
        int userId = ((User) session.getAttribute("user")).getId();
        Order order = orderService.findById(id);

        if(order==null || order.getTableDinner().getEatery().getUser().getId()!=userId){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }
        List<OrderItem> orderItemList = orderItemService.findAllByOrderId(id);
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        for(OrderItem orderItem : orderItemList){
            orderItemDtoList.add(orderItem.toDto());
        }

        return ResponseEntity.status(HttpStatus.OK).body(orderItemDtoList);
    }

    @PostMapping(value = "/order/add")
    public ResponseEntity<?> addOrder(@RequestBody OrderDto orderDto){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        TableDinner tableDinner = tableDinnerService.findById(orderDto.getTableDinnerId());
        if(tableDinner==null || tableDinner.getEatery().getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }

        Order order = mapperService.toOrder(orderDto);
        orderService.saveOrder(order);
        orderDto.setId(order.getId());
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }


}
