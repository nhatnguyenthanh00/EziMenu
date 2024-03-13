package com.example.ezimenu.service.serviceimpl;

import com.example.ezimenu.entity.User;
import com.example.ezimenu.repository.IUserRepository;
import com.example.ezimenu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    IUserRepository userRepository;
    @Override
    public User findUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }
    @Override
    public User findUserByUsernameAndPassword(String username, String password){
        return userRepository.findUserByUsernameAndPassword(username,password);
    }

    @Override
    public User saveUser(User user){
        return userRepository.save(user);
    }
}
