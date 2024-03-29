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
import java.util.stream.Collectors;

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
    public ResponseEntity<?> getAllDishByEatery(@PathVariable int id,
                                                @RequestParam(name = "search", defaultValue = "") String search,
                                                @RequestParam(name = "cateId", defaultValue = "0") Integer cateId){
        Eatery eatery = eateryService.findById(id);

        if(eatery==null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found eatery.");
        }

        List<Dish> dishList = dishService.findAllByEateryId(id);
        dishList =  dishList.stream()
                .filter(dish -> dish.getName().toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toList());
        if(cateId!=0) {
            dishList = dishList.stream()
                    .filter(dish -> dish.getCategory().getId() == cateId)
                    .collect(Collectors.toList());
        }
        List<DishDto> dishDtoList = new ArrayList<>();
        for(Dish dish : dishList){
            dishDtoList.add(dish.toDto());
        }
        return ResponseEntity.status(HttpStatus.OK).body(dishDtoList);
    }

    @GetMapping(value = "/category/{id}/dishes")
    public ResponseEntity<?> dishPageByCategory(@PathVariable int id){
        Category category = categoryService.findById(id);

        if(category==null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found category.");
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
        Dish dish = dishService.findById(id);

        if(dish==null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found dish.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(dish.toDto());
    }

    @PostMapping(value = "/dish/add")
    public ResponseEntity<?> addDish(@RequestBody DishDto dishDto){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int categoryId = dishDto.getCategoryId();
        Category category = categoryService.findById(categoryId);
        if(category==null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found category.");
        }
        if(category.getEatery().getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can't access this eatery.");
        }
        String name = dishDto.getName();
        int price = dishDto.getPrice();

        Dish existedDish = dishService.findDishByCategoryAndNameAndPrice(category,name,price);
        if (existedDish != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dish existed.");
        }

        Dish dish = mapperService.toDish(dishDto);
        dishService.saveDish(dish);
        dishDto.setId(dish.getId());
        return ResponseEntity.status(HttpStatus.OK).body(dishDto);
    }
    @PutMapping(value = "/dish/{dishid}/update")
    public ResponseEntity<?> updateDish(@PathVariable int dishid,@RequestBody DishDto dishDto){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Dish dish = dishService.findById(dishid);
        if(dish==null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found dish.");
        }
        if(dish.getCategory().getEatery().getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can't access this eatery.");
        }
        int categoryId = dishDto.getCategoryId();
        Category updateCategory = categoryService.findById(categoryId);
        if(updateCategory==null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found category.");
        }
        if(updateCategory.getEatery().getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can't access this category.");
        }
        String name = dishDto.getName();
        int price = dishDto.getPrice();
        Dish existedDish = dishService.findDishByCategoryAndNameAndPrice(updateCategory,name,price);
        if (existedDish != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dish existed.");
        }
        dish.setName(name);
        dish.setPrice(price);
        dish.setCategory(updateCategory);
        dishService.saveDish(dish);
        return ResponseEntity.status(HttpStatus.OK).body(dish.toDto());
    }

    @PutMapping(value = "/dish/{dishid}/update-status")
    public ResponseEntity<?> updateDishStatus(@PathVariable int dishid){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Dish dish = dishService.findById(dishid);
        if(dish==null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found dish.");
        }
        if(dish.getCategory().getEatery().getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can't access this eatery.");
        }

        boolean status = dish.isStatus();
        dish.setStatus(!status);
        dishService.saveDish(dish);
        return ResponseEntity.status(HttpStatus.OK).body(dish.toDto());
    }

    @DeleteMapping(value = "/dish/{id}/delete")
    public ResponseEntity<?> deleteDish(@PathVariable int id){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Dish dish = dishService.findById(id);
        if(dish == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Delete fail. Not found dish.");
        }
        if(dish.getCategory().getEatery().getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Delete fail. You can't access this dish.");
        }
        boolean kt = dishService.deleteById(id);
        if(kt == false) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Delete dish fail.");
        return ResponseEntity.status(HttpStatus.OK).body("Delete dish successful.");
    }


    
}
