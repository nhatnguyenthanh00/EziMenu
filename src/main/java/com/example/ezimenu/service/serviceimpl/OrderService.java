package com.example.ezimenu.service.serviceimpl;

import com.example.ezimenu.entity.Order;
import com.example.ezimenu.repository.IOrderRepository;
import com.example.ezimenu.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderService {
    @Autowired
    IOrderRepository orderRepository;
    @Override
    public Order findById(int id){
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findAllByEateryId(int eateryId){
        return orderRepository.findAllByEateryId(eateryId);
    }

    @Override
    public List<Order> findAllByTableDinnerId(int tableDinnerId){
        return orderRepository.findAllByTableDinnerId(tableDinnerId);
    }

    @Override
    public Order saveOrder(Order order){
        return orderRepository.save(order);
    }
}
