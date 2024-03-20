package com.example.ezimenu.controller.User;

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

    @GetMapping(value = "/eatery/{id}/dish/{dishid}")
    public ResponseEntity<?> getDishById(@PathVariable int id, @PathVariable int dishid){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Dish dish = dishService.findById(dishid);

        if(dish==null || dish.getCategory().getEatery().getId()!=id ||dish.getCategory().getEatery().getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(dish.toDto());
    }

    @PostMapping(value = "/eatery/{id}/dish/add")
    public ResponseEntity<?> addDish(@PathVariable int id, @RequestBody DishDto dishDto){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Eatery eatery = eateryService.findById(id);
        if(eatery==null || eatery.getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }

        Dish dish = mapperService.toDish(dishDto);
        dishService.saveDish(dish);
        dishDto.setId(dish.getId());
        return ResponseEntity.status(HttpStatus.OK).body(dishDto);
    }
    @PutMapping(value = "/eatery/{id}/dish/{dishid}/update")
    public ResponseEntity<?> updateDish(@PathVariable int id,@PathVariable int dishid,@RequestBody DishDto dishDto){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Dish dish = dishService.findById(dishid);
        if(dish == null || dish.getCategory().getEatery().getId()!=id || dish.getCategory().getEatery().getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }
        dish.setName(dishDto.getName());
        dish.setPrice(dishDto.getPrice());
        dish.setStatus(dishDto.isStatus());
        dish.setCategory(categoryService.findById(dishDto.getCategoryId()));
        dishService.saveDish(dish);
        return ResponseEntity.status(HttpStatus.OK).body(dishDto);
    }

    @DeleteMapping(value = "/eatery/{id}/dish/{dishid}/delete")
    public ResponseEntity<?> deleteDish(@PathVariable int id, @PathVariable int dishid){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Dish dish = dishService.findById(dishid);
        if(dish == null || dish.getCategory().getEatery().getId()!=id || dish.getCategory().getEatery().getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }
        boolean kt = dishService.deleteById(dishid);
        if(kt == false) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Delete dish fail.");
        return ResponseEntity.status(HttpStatus.OK).body("Delete dish successful.");
    }


    
}
