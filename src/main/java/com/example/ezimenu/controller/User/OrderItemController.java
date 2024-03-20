package com.example.ezimenu.controller.User;

import com.example.ezimenu.dto.OrderItemDto;
import com.example.ezimenu.entity.OrderItem;
import com.example.ezimenu.service.serviceimpl.OrderItemService;
import com.example.ezimenu.service.transer.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderItemController {
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    MapperService mapperService;
    @GetMapping(value = "/order-item/{id}")
    public ResponseEntity<?> getOrderItemById(@PathVariable int id){
        OrderItem orderItem = orderItemService.findById(id);
        if(orderItem == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You have not access");
        return ResponseEntity.status(HttpStatus.OK).body(orderItem.toDto());
    }
    @PostMapping(value = "/order-item/add")
    public ResponseEntity<?> addOrderItemById(@RequestBody OrderItemDto orderItemDto){
        int orderId = orderItemDto.getOrderId();
        int dishId = orderItemDto.getDishId();
        int quantity = orderItemDto.getQuantity();
        OrderItem existOrderItem = orderItemService.findByOrderIdAndDishId(orderId,dishId);
        if(existOrderItem == null){
            OrderItem orderItem = mapperService.toOrderItem(orderItemDto);
            orderItemService.saveOrderItem(orderItem);
            orderItemDto.setId(orderItem.getId());
            return ResponseEntity.status(HttpStatus.OK).body(orderItemDto);
        }
        existOrderItem.setQuantity(quantity + existOrderItem.getQuantity());
        orderItemService.saveOrderItem(existOrderItem);
        return ResponseEntity.status(HttpStatus.OK).body(existOrderItem.toDto());
    }
    @PutMapping(value = "/order-item/{id}/update")
    public ResponseEntity<?> updateOrderItem(@PathVariable int id, @RequestBody OrderItemDto orderItemDto){
        OrderItem orderItem = orderItemService.findById(id);
        if(orderItem == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have not access.");
        }
        int quantity = orderItemDto.getQuantity();
        boolean status = orderItemDto.isStatus();
        orderItem.setStatus(status);
        orderItem.setQuantity(quantity);
        orderItemService.saveOrderItem(orderItem);
        return ResponseEntity.status(HttpStatus.OK).body(orderItemDto);
    }
    @DeleteMapping(value = "/order-item/{id}/delete")
    public ResponseEntity<?> deleteOrderItem(@PathVariable int id){
        OrderItem orderItem = orderItemService.findById(id);
        if(orderItem == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have not access.");
        }
        boolean kt = orderItemService.deleteById(id);
        if(kt == false)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't delete because of order has been sent.");
        return ResponseEntity.status(HttpStatus.OK).body("Delete order item successful.");
    }
}
