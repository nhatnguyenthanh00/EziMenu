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
}
