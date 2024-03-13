package com.example.ezimenu.controller.ManageEateryController;

import com.example.ezimenu.dto.EateryDto;
import com.example.ezimenu.entity.Eatery;
import com.example.ezimenu.entity.User;
import com.example.ezimenu.service.serviceimpl.EateryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    EateryService eateryService;

    @GetMapping(value = "/eateries")
    public ResponseEntity<List<EateryDto>> eateryPage(){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<Eatery> eateryList = eateryService.findAllByUserId(user.getId());
        List<EateryDto> eateryDtoList = new ArrayList<>();
        for (Eatery eatery : eateryList) {
            EateryDto eateryDto = new EateryDto();
            eateryDto.setId(eatery.getId());
            eateryDto.setUserId(eatery.getUser().getId());
            eateryDto.setAddress(eatery.getAddress());
            eateryDto.setDescription(eatery.getDescription());
            eateryDtoList.add(eateryDto);
        }
        return ResponseEntity.ok(eateryDtoList);
    }

    @GetMapping(value = "/eatery/{id}")
    public ResponseEntity<EateryDto> getEateryById(@PathVariable int id){
        Eatery eatery = eateryService.findById(id);
        EateryDto eateryDto = new EateryDto();
        if(eatery==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(eateryDto);
        }
        eateryDto.setId(eatery.getId());
        eateryDto.setUserId(eatery.getUser().getId());
        eateryDto.setAddress(eatery.getAddress());
        eateryDto.setDescription(eatery.getDescription());
        return ResponseEntity.status(HttpStatus.OK).body(eateryDto);
//        if(eatery == null)
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Eatery dón't existed.");
    }

    @PostMapping(value = "/add-eatery")
    public ResponseEntity<String> addEatery(@RequestBody Eatery eatery){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<Eatery> eateryList = eateryService.findAllByUserId(user.getId());
        if(eateryList.size()==1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Just can add only one eatery.");
        }
        String address = eatery.getAddress();
        String description = eatery.getDescription();
        if(address == null || description == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Need to fill in all information.");
        }
        eatery.setUser(user);
        eateryService.saveEatery(eatery);
        return ResponseEntity.ok("Add new eatery successful.");
    }
}
