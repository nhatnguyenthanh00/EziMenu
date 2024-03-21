package com.example.ezimenu.service;

import com.example.ezimenu.entity.User;

public interface IUserService {
    User findUserByUsername(String username);
    User findUserByUsernameAndPassword(String username, String password);

    User saveUser(User user);

    User findByUserId(int userId);
}
