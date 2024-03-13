package com.example.ezimenu.service.serviceimpl;

import com.example.ezimenu.entity.Category;
import com.example.ezimenu.repository.ICategoryRepository;
import com.example.ezimenu.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    ICategoryRepository categoryRepository;
    @Override
    public Category saveCategory(Category category){
        return categoryRepository.save(category);
    }
}
