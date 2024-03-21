package com.example.ezimenu.service.serviceimpl;

import com.example.ezimenu.entity.Category;
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

    @Override
    public List<Dish> findAllByCategoryId(int categoryId){
        return dishRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public Dish findById(int id){
        return dishRepository.findById(id);
    }

    @Override
    public Dish saveDish(Dish dish){
        return dishRepository.save(dish);
    }

    public boolean deleteById(int id){
        Dish dish = dishRepository.findById(id);
        if(dish == null) return false;
        dishRepository.deleteById(id);
        return true;
    }

    @Override
    public Dish findDishByCategoryAndNameAndPrice(Category category, String name, int price){
        return dishRepository.findDishByCategoryAndNameAndPrice(category, name, price);
    }
}
