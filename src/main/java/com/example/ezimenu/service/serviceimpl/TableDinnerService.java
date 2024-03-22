package com.example.ezimenu.service.serviceimpl;

import com.example.ezimenu.entity.Category;
import com.example.ezimenu.entity.TableDinner;
import com.example.ezimenu.repository.ITableDinnerRepository;
import com.example.ezimenu.service.ITableDinnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableDinnerService implements ITableDinnerService {
    @Autowired
    ITableDinnerRepository tableDinnerRepository;
    @Override
    public TableDinner saveTableDinner(TableDinner tableDinner){
        return tableDinnerRepository.save(tableDinner);
    }

    @Override
    public List<TableDinner> findAllByEateryId(int eateryId){
        return tableDinnerRepository.findAllByEateryId(eateryId);
    }

    @Override
    public TableDinner findById(int id){
        return tableDinnerRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id){
        TableDinner tableDinner = tableDinnerRepository.findById(id);
        if(tableDinner == null) return false;
        tableDinnerRepository.deleteById(id);
        return true;
    }

    @Override
    public     TableDinner findTableDinnerByEateryIdAndAndDescription(int eateryId, String description){
        return tableDinnerRepository.findTableDinnerByEatery_IdAndAndDescription(eateryId,description);
    }
}
