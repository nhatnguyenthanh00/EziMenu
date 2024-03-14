package com.example.ezimenu.controller.ManageDishController;

import com.example.ezimenu.dto.DishDto;
import com.example.ezimenu.entity.Dish;
import com.example.ezimenu.entity.Eatery;
import com.example.ezimenu.entity.User;
import com.example.ezimenu.service.serviceimpl.DishService;
import com.example.ezimenu.service.serviceimpl.EateryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DishController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    EateryService eateryService;
    @Autowired
    DishService dishService;
    @GetMapping(value = "/eatery/{id}/dishes")
    public ResponseEntity<?> dishPage(@PathVariable int id){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Eatery eatery = eateryService.findById(id);

        if(eatery==null || eatery.getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }

        List<Dish> dishList = dishService.findAllByEateryId(id);
        List<DishDto> dishDtoList = new ArrayList<>();
        for(Dish dish : dishList){
            System.out.println(dish.getName() + " " + dish.getPrice());
        }


        return null;
    }
    
}
