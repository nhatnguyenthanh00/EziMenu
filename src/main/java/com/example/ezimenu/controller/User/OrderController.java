package com.example.ezimenu.controller.User;

import com.example.ezimenu.dto.DishDto;
import com.example.ezimenu.dto.OrderDto;
import com.example.ezimenu.dto.OrderItemDto;
import com.example.ezimenu.entity.*;
import com.example.ezimenu.service.serviceimpl.*;
import com.example.ezimenu.service.transer.MapperService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
    DishService dishService;
    @Autowired
    MapperService mapperService;
    @GetMapping(value = "/eatery/{id}/orders")
    public ResponseEntity<?> getAllOrderByTable(@PathVariable int id){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Eatery eatery = eateryService.findById(id);

        if(eatery==null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found eatery.");
        }
        if(eatery.getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access this eatery.");
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
    @GetMapping(value = "/table/{id}/orders/{status}")
    public ResponseEntity<?> getAllOrderByTableIdAndStatus(@PathVariable int id,@PathVariable int status){
        List<Order> orderList = orderService.findAllByTableDinnerIdAndStatus(id,status);
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
        int totalPrice = 0, price;
        for(OrderItem orderItem : orderItemList){
            price = dishService.findById(orderItem.getDish().getId()).getPrice();
            totalPrice += price * orderItem.getQuantity();
        }
        order.setTotalPrice(totalPrice);
        orderService.saveOrder(order);
        return ResponseEntity.status(HttpStatus.OK).body(order.toDto());
    }


    @PostMapping(value = "/order/add")
    public ResponseEntity<?> addOrder(@Valid  @RequestBody OrderDto orderDto){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int tableDinnerId = orderDto.getTableDinnerId();
        TableDinner tableDinner = tableDinnerService.findById(tableDinnerId);
        if(tableDinner==null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found table dinner.");
        }
        if(tableDinner.getEatery().getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access this eatery.");
        }

        Order order = mapperService.toOrder(orderDto);
        orderService.saveOrder(order);
        orderDto.setId(order.getId());
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @PutMapping(value = "/order/{id}/update-status")
    public ResponseEntity<?> updateOrder(@PathVariable int id,@RequestParam(name = "status") Integer status){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Order order = orderService.findById(id);
        if(order == null ){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found order.");
        }
        if(order.getTableDinner().getEatery().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access this order.");
        }
        if(order.getStatus()==-1 && status==0){
            TableDinner tableDinner = order.getTableDinner();
            Order newPendingOrder = new Order();
            newPendingOrder.setTableDinner(tableDinner);
            orderService.saveOrder(newPendingOrder);
        }
        order.setStatus(status);
        orderService.saveOrder(order);
        return ResponseEntity.status(HttpStatus.OK).body(order.toDto());
    }

    @DeleteMapping(value = "/order/{id}/delete")
    public ResponseEntity<?> deleteOrderById(@PathVariable int id){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Order order = orderService.findById(id);
        if(order == null ){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found order.");
        }
        if(order.getTableDinner().getEatery().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access this order.");
        }
        if(order.getStatus() !=-1)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Can not delete, order has been sent.");
        orderService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Delete order successful.");
    }


}
