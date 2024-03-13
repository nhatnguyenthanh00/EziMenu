package com.example.ezimenu.service.serviceimpl;

import com.example.ezimenu.entity.Category;
import com.example.ezimenu.repository.ICategoryRepository;
import com.example.ezimenu.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    ICategoryRepository categoryRepository;
    @Override
    public Category saveCategory(Category category){
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAllByEateryId(int eateryId){
        return categoryRepository.findAllByEateryId(eateryId);
    }

    @Override
    public Category findById(int id){
        return categoryRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id){
        Category category = categoryRepository.findById(id);
        if(category == null) return false;
        categoryRepository.deleteById(id);
        return true;
    }
}
