package com.example.ezimenu.service.serviceimpl;

import com.example.ezimenu.entity.OrderItem;
import com.example.ezimenu.repository.IOrderItemRepository;
import com.example.ezimenu.service.IOderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService implements IOderItemService {
    @Autowired
    IOrderItemRepository orderItemRepository;
    @Override
    public OrderItem findById(int id){
        return orderItemRepository.findById(id);
    }

    @Override
    public List<OrderItem> findAllByOrderId(int orderId){
        return orderItemRepository.findAllByOrderId(orderId);
    }

    @Override
    public OrderItem findByOrderIdAndDishId(int orderId, int dishId){
        return orderItemRepository.findByOrderIdAndDishId(orderId,dishId);
    }

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem){
        return orderItemRepository.save(orderItem);
    }

    @Override
    public boolean deleteById(int id){
        OrderItem orderItem = orderItemRepository.findById(id);
        if(orderItem == null) return false;
        if(orderItem.getOrder().getStatus()!=-1) return false;
        orderItemRepository.deleteById(id);
        return true;
    }
}
