package com.example.ezimenu.controller.ManageDishController;

import com.example.ezimenu.dto.DishDto;
import com.example.ezimenu.entity.Category;
import com.example.ezimenu.entity.Dish;
import com.example.ezimenu.entity.Eatery;
import com.example.ezimenu.entity.User;
import com.example.ezimenu.service.serviceimpl.CategoryService;
import com.example.ezimenu.service.serviceimpl.DishService;
import com.example.ezimenu.service.serviceimpl.EateryService;
import com.example.ezimenu.service.transer.MapperService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DishController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    MapperService mapperService;
    @Autowired
    EateryService eateryService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    DishService dishService;
    @GetMapping(value = "/eatery/{id}/dishes")
    public ResponseEntity<?> dishPageByEatery(@PathVariable int id){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Eatery eatery = eateryService.findById(id);

        if(eatery==null || eatery.getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }

        List<Dish> dishList = dishService.findAllByEateryId(id);
        List<DishDto> dishDtoList = new ArrayList<>();
        for(Dish dish : dishList){
            dishDtoList.add(dish.toDto());
        }
        return ResponseEntity.status(HttpStatus.OK).body(dishDtoList);
    }

    @GetMapping(value = "/category/{id}/dishes")
    public ResponseEntity<?> dishPageByCategory(@PathVariable int id){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Category category = categoryService.findById(id);

        if(category==null || category.getEatery().getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }

        List<Dish> dishList = dishService.findAllByCategoryId(id);
        List<DishDto> dishDtoList = new ArrayList<>();
        for(Dish dish : dishList){
            dishDtoList.add(dish.toDto());
        }
        return ResponseEntity.status(HttpStatus.OK).body(dishDtoList);
    }

    @GetMapping(value = "/dish/{id}")
    public ResponseEntity<?> getDishById(@PathVariable int id){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Dish dish = dishService.findById(id);

        if(dish==null || dish.getCategory().getEatery().getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(dish.toDto());
    }

    @PostMapping(value = "/eatery/{id}/dish/add")
    public ResponseEntity<?> addCategory(@PathVariable int id, @RequestBody DishDto dishDto){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Eatery eatery = eateryService.findById(id);
        if(eatery==null || eatery.getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }

        Dish dish = mapperService.toDish(dishDto);
        dishService.saveDish(dish);
        return ResponseEntity.status(HttpStatus.OK).body(dishDto);
    }

    
}
