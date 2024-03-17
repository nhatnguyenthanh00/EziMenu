package com.example.ezimenu.controller;

import com.example.ezimenu.dto.CategoryDto;
import com.example.ezimenu.dto.EateryDto;
import com.example.ezimenu.entity.Category;
import com.example.ezimenu.entity.Eatery;
import com.example.ezimenu.entity.User;
import com.example.ezimenu.repository.ICategoryRepository;
import com.example.ezimenu.service.serviceimpl.CategoryService;
import com.example.ezimenu.service.serviceimpl.EateryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    CategoryService categoryService;
    @Autowired
    EateryService eateryService;
    @GetMapping(value = "/eatery/{id}/categories")
    public ResponseEntity<?> categoryPage(@PathVariable int id){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Eatery eatery = eateryService.findById(id);

        if(eatery==null || eatery.getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }
        List<Category> categoryList = categoryService.findAllByEateryId(id);
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for(Category category : categoryList){
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setEateryId(category.getEatery().getId());
            categoryDto.setName(category.getName());
            categoryDtoList.add(categoryDto);
        }
        return ResponseEntity.ok(categoryDtoList);
    }
    @GetMapping(value = "/eatery/{id}/category/{cateid}")
    public ResponseEntity<?> getCategoryById(@PathVariable int id, @PathVariable int cateid){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Eatery eatery = eateryService.findById(id);
        if(eatery==null || eatery.getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }
        Category category = categoryService.findById(id);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setEateryId(category.getEatery().getId());
        categoryDto.setName(category.getName());
        return ResponseEntity.ok(categoryDto);
    }
    @PostMapping(value = "/eatery/{id}/category/add")
    public ResponseEntity<?> addCategory(@PathVariable int id, @RequestBody Category category){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Eatery eatery = eateryService.findById(id);
        if(eatery==null || eatery.getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }
        category.setEatery(eatery);
        categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body("Add category successful.");

    }
    @PutMapping(value = "/eatery/{id}/category/{cateid}/update")
    public ResponseEntity<?> addCategory(@PathVariable int id, @PathVariable int cateid, @RequestBody Category category) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Eatery eatery = eateryService.findById(id);
        if(eatery==null || eatery.getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }
        Category updateCategory = categoryService.findById(cateid);
        updateCategory.setName(category.getName());
        categoryService.saveCategory(updateCategory);
        return ResponseEntity.status(HttpStatus.OK).body("Update category successful.");
    }

    @DeleteMapping(value = "/eatery/{id}/category/{cateid}/delete")
    public ResponseEntity<?> deleteCategory(@PathVariable int id, @PathVariable int cateid){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Eatery eatery = eateryService.findById(id);
        if(eatery==null || eatery.getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }
        boolean kt = categoryService.deleteById(cateid);
        if(kt == false)
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Delete fail.");
        return ResponseEntity.status(HttpStatus.OK).body("Delete successful.");
    }
}
