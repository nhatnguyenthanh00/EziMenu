package com.example.ezimenu.repository;

import com.example.ezimenu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);
    User findUserByUsernameAndPassword(String username, String password);
}
