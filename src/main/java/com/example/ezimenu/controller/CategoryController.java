package com.example.ezimenu.controller;

import com.example.ezimenu.dto.EateryDto;
import com.example.ezimenu.entity.Category;
import com.example.ezimenu.entity.User;
import com.example.ezimenu.repository.ICategoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    ICategoryRepository categoryRepository;
    @GetMapping(value = "/eatery/{id}/categories")
    public ResponseEntity<List<Category>> categoryPage(@PathVariable int id){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        return  null;
    }
}
