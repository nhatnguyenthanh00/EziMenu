package com.example.ezimenu.service.serviceimpl;

import com.example.ezimenu.entity.Notify;
import com.example.ezimenu.repository.INotifyRepository;
import com.example.ezimenu.service.INotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotifyService implements INotifyService {
    @Autowired
    INotifyRepository notifyRepository;
    @Override
    public Notify findById(int id) {
        return notifyRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteByid(int id) {
        Notify notify = notifyRepository.findById(id).orElse(null);
        if(notify == null) return false;
        notifyRepository.deleteById(id);
        return true;
    }

    @Override
    public Notify saveNotify(Notify notify) {
        return notifyRepository.save(notify);
    }

    @Override
    public List<Notify> findAllByEateryId(int eateryId) {
        return notifyRepository.findAllByEateryId(eateryId);
    }

    @Override
    public List<Notify> findAllByTableDinnerId(int tableDinnerId) {
        return notifyRepository.findAllByTableDinnerId(tableDinnerId);
    }

    @Override
    public boolean deleteById(int id){
        Notify notify = notifyRepository.findById(id).orElse(null);
        if(notify == null) return false;
        notifyRepository.deleteById(id);
        return true;
    }
}
