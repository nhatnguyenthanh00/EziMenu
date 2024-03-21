package com.example.ezimenu.controller.User;

import com.example.ezimenu.dto.CategoryDto;
import com.example.ezimenu.dto.EateryDto;
import com.example.ezimenu.entity.Category;
import com.example.ezimenu.entity.Eatery;
import com.example.ezimenu.entity.User;
import com.example.ezimenu.repository.ICategoryRepository;
import com.example.ezimenu.service.serviceimpl.CategoryService;
import com.example.ezimenu.service.serviceimpl.EateryService;
import com.example.ezimenu.service.transer.MapperService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    CategoryService categoryService;
    @Autowired
    EateryService eateryService;
    @Autowired
    MapperService mapperService;
    @GetMapping(value = "/eatery/{id}/categories")
    public ResponseEntity<?> categoryPage(@PathVariable int id){
        Eatery eatery = eateryService.findById(id);
        if(eatery==null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found eatery.");
        }
        List<Category> categoryList = categoryService.findAllByEateryId(id);
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for(Category category : categoryList){
            categoryDtoList.add(category.toDto());
        }
        return ResponseEntity.ok(categoryDtoList);
    }
    @GetMapping(value = "/category/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable int id){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Category category = categoryService.findById(id);
        if(category == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found category.");
        }
        if(category.getEatery().getUser().getId()!= user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can't access this category.");
        }
        return ResponseEntity.ok(category.toDto());
    }
    @PostMapping(value = "/category/add")
    public ResponseEntity<?> addCategory(@Valid  @RequestBody CategoryDto categoryDto){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int eateryId = categoryDto.getEateryId();
        Eatery eatery = eateryService.findById(eateryId);
        if(eatery==null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Add fail. Not found eatery.");
        }
        if(eatery.getUser().getId()!= user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Add fail. You can't access this eatery.");
        }
        String name = categoryDto.getName();
        if(name.trim().isEmpty()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Add fail. Name can't be empty.");
        }
        Category existedCategory = categoryService.findCategoryByEateryIdAndName(eateryId,name);
        if(existedCategory!=null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Add fail. This category existed.");
        }
        Category category = mapperService.toCategory(categoryDto);
        categoryService.saveCategory(category);
        categoryDto.setId(category.getId());
        return ResponseEntity.status(HttpStatus.OK).body(categoryDto);

    }
    @PutMapping(value = "/category/{id}/update")
    public ResponseEntity<?> addCategory(@PathVariable int id, @Valid @RequestBody CategoryDto categoryDto) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Category category = categoryService.findById(id);
        if(category == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Update fail. Not found category.");
        }
        if(category.getEatery().getUser().getId()!= user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Update fail. You can't access this category.");
        }
        int eateryId = category.getEatery().getId();
        String name = categoryDto.getName();
        Category existedCategory = categoryService.findCategoryByEateryIdAndName(eateryId,name);
        if(existedCategory!=null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update fail. This category existed.");
        }
        category.setName(name);
        categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body(category.toDto());
    }

    @DeleteMapping(value = "/category/{id}/delete")
    public ResponseEntity<?> deleteCategory(@PathVariable int id){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Category category = categoryService.findById(id);
        if(category == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Delete fail. Not found category.");
        }
        if(category.getEatery().getUser().getId()!= user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Delete fail. You can't access this category.");
        }
        boolean kt = categoryService.deleteById(id);
        if(kt == false)
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Delete fail.");
        return ResponseEntity.status(HttpStatus.OK).body("Delete successful.");
    }
}
