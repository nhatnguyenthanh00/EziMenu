package com.example.ezimenu.service.serviceimpl;

import com.example.ezimenu.entity.Eatery;
import com.example.ezimenu.repository.IEateryRepository;
import com.example.ezimenu.service.IEateryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EateryService implements IEateryService {
    @Autowired
    IEateryRepository eateryRepository;
    @Override
    public Eatery saveEatery(Eatery eatery){
        return eateryRepository.save(eatery);
    }

    @Override
    public List<Eatery> findAllByUserId(int userid){
        return eateryRepository.findAllByUserId(userid);
    }

    @Override
    public Eatery findById(int id){
        return eateryRepository.findById(id);
    }
}
