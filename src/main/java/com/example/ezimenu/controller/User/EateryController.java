package com.example.ezimenu.controller.User;

import com.example.ezimenu.dto.EateryDto;
import com.example.ezimenu.entity.Eatery;
import com.example.ezimenu.entity.User;
import com.example.ezimenu.service.serviceimpl.EateryService;
import com.example.ezimenu.service.transer.MapperService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EateryController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    EateryService eateryService;
    @Autowired
    MapperService mapperService;

    @GetMapping(value = "/eateries")
    public ResponseEntity<List<EateryDto>> eateryPage(){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<Eatery> eateryList = eateryService.findAllByUserId(user.getId());
        List<EateryDto> eateryDtoList = new ArrayList<>();
        for (Eatery eatery : eateryList) {
            eateryDtoList.add(eatery.toDto());
        }
        return ResponseEntity.ok(eateryDtoList);
    }

    @GetMapping(value = "/eatery/{id}")
    public ResponseEntity<?> getEateryById(@PathVariable int id){
        Eatery eatery = eateryService.findById(id);
        if(eatery==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Eatery not existed.");
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(eatery.getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access this eatery.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(eatery.toDto());
    }

    @PostMapping(value = "/eatery/add")
    public ResponseEntity<?> addEatery(@RequestBody EateryDto eateryDto){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<Eatery> eateryList = eateryService.findAllByUserId(user.getId());
        if(eateryList.size()==1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Just can add only one eatery.");
        }
        String address = eateryDto.getAddress();
        String description = eateryDto.getDescription();
        if(address == null || description == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The request body contains invalid data.");
        }
        Eatery eatery = mapperService.toEatery(eateryDto);
        eateryService.saveEatery(eatery);
        eateryDto.setId(eatery.getId());
        return ResponseEntity.ok(eateryDto);
    }
}
