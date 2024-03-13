package com.example.ezimenu.controller;

import com.example.ezimenu.dto.UserDto;
import com.example.ezimenu.entity.User;
import com.example.ezimenu.service.serviceimpl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    private HttpServletRequest request;
    @GetMapping(value = "/")
    public ResponseEntity<String> homePage(){
        HttpSession session = request.getSession(false);
        if(session != null){
            User user = (User) session.getAttribute("user");
            if(user!=null) return ResponseEntity.ok("Hello "+user.getFullName());
        }
        return ResponseEntity.ok("Welcome to HomePage");
    }
    @GetMapping(value = "/login")
    public ResponseEntity<String> loginPage(){
        return ResponseEntity.ok("Welcome to Login Page");
    }
    @PostMapping(value = "/login")
    public ResponseEntity<String> checkLogin(@RequestBody UserDto userDto){
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        System.out.println("username: "+username);
        System.out.println("password: "+password);
        User user = userService.findUserByUsername(username);
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username not existed.");
        }
        user = userService.findUserByUsernameAndPassword(username,password);
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password incorrect.");
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        return ResponseEntity.ok("Login successful");
    }
    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("Logout successful.");
    }
    @PostMapping(value = "/signup")
    public ResponseEntity<String> signup(@RequestBody User user){
        String username = user.getUsername();
        if (userService.findUserByUsername(username)!=null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists.");
        }
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setFullName(user.getFullName());
        newUser.setPhoneNumber(user.getPhoneNumber());
        userService.saveUser(user);
        return ResponseEntity.ok("Signup successful.");
    }
}
