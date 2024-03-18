package com.example.ezimenu.controller;

import com.example.ezimenu.dto.CategoryDto;
import com.example.ezimenu.dto.TableDinnerDto;
import com.example.ezimenu.entity.Category;
import com.example.ezimenu.entity.Eatery;
import com.example.ezimenu.entity.TableDinner;
import com.example.ezimenu.entity.User;
import com.example.ezimenu.service.serviceimpl.CategoryService;
import com.example.ezimenu.service.serviceimpl.EateryService;
import com.example.ezimenu.service.serviceimpl.TableDinnerService;
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
public class TableDinnerController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    MapperService mapperService;
    @Autowired
    TableDinnerService tableDinnerService;
    @Autowired
    EateryService eateryService;
    @GetMapping(value = "/eatery/{id}/table-dinners")
    public ResponseEntity<?> tableDinnerPage(@PathVariable int id){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Eatery eatery = eateryService.findById(id);

        if(eatery==null || eatery.getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }
        List<TableDinner> tableDinnerList = tableDinnerService.findAllByEateryId(id);
        List<TableDinnerDto> tableDinnerDtoList = new ArrayList<>();
        for(TableDinner tableDinner : tableDinnerList){
            tableDinnerDtoList.add(tableDinner.toDto());
        }
        return ResponseEntity.ok(tableDinnerDtoList);
    }
    @GetMapping(value = "/eatery/{id}/table-dinner/{tableDinnerId}")
    public ResponseEntity<?> getTableDinnerById(@PathVariable int id, @PathVariable int tableDinnerId){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Eatery eatery = eateryService.findById(id);
        if(eatery==null || eatery.getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }
        TableDinner tableDinner = tableDinnerService.findById(tableDinnerId);
        if(tableDinner == null || tableDinner.getEatery().getId()!=id) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access..");
        TableDinnerDto tableDinnerDto = tableDinner.toDto();
        return ResponseEntity.ok(tableDinnerDto);
    }
    @PostMapping(value = "/eatery/{id}/table-dinner/add")
    public ResponseEntity<?> addCategory(@PathVariable int id, @RequestBody TableDinnerDto tableDinnerDto){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Eatery eatery = eateryService.findById(id);
        if(eatery==null || eatery.getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }
        TableDinner tableDinner = mapperService.toTableDinner(tableDinnerDto);
        tableDinnerService.saveTableDinner(tableDinner);
        tableDinnerDto.setId(tableDinner.getId());
        return ResponseEntity.status(HttpStatus.OK).body(tableDinnerDto);

    }
    @PutMapping(value = "/eatery/{id}/table-dinner/{tableDinnerId}/update")
    public ResponseEntity<?> addTableDinner(@PathVariable int id, @PathVariable int tableDinnerId, @RequestBody TableDinnerDto tableDinnerDto) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Eatery eatery = eateryService.findById(id);
        if(eatery==null || eatery.getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }
        TableDinner tableDinner= tableDinnerService.findById(tableDinnerId);
        if(tableDinner == null || tableDinner.getEatery().getId()!=id){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }
        tableDinner.setDescription(tableDinnerDto.getDescription());
        tableDinner.setStatus(tableDinnerDto.isStatus());
        tableDinnerService.saveTableDinner(tableDinner);
        return ResponseEntity.status(HttpStatus.OK).body(tableDinnerDto);
    }

    @DeleteMapping(value = "/eatery/{id}/table-dinner/{tableDinnerId}/delete")
    public ResponseEntity<?> deleteTableDinner(@PathVariable int id, @PathVariable int tableDinnerId){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Eatery eatery = eateryService.findById(id);
        if(eatery==null || eatery.getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }

        TableDinner tableDinner = tableDinnerService.findById(tableDinnerId);
        if(tableDinner == null || tableDinner.getEatery().getId()!=id){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }
        boolean kt = tableDinnerService.deleteById(tableDinnerId);
        if(kt == false) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Delete table dinner fail.");
        return ResponseEntity.status(HttpStatus.OK).body("Delete table dinner successful.");
    }
}
