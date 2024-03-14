package com.example.ezimenu.service.serviceimpl;

import com.example.ezimenu.entity.Dish;
import com.example.ezimenu.repository.IDishRepository;
import com.example.ezimenu.service.IDishService;
import jakarta.persistence.OneToMany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService implements IDishService {
    @Autowired
    IDishRepository dishRepository;
    @Override
    public List<Dish> findAllByEateryId(int eateryId){
        return dishRepository.findAllByEateryId(eateryId);
    }
}
