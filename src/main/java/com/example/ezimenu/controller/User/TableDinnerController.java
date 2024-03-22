package com.example.ezimenu.controller.User;

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
import jakarta.validation.Valid;
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

        if(eatery==null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found eatery.");
        }
        if(eatery.getUser().getId()!= user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access this eatery.");
        }
        List<TableDinner> tableDinnerList = tableDinnerService.findAllByEateryId(id);
        List<TableDinnerDto> tableDinnerDtoList = new ArrayList<>();
        for(TableDinner tableDinner : tableDinnerList){
            tableDinnerDtoList.add(tableDinner.toDto());
        }
        return ResponseEntity.ok(tableDinnerDtoList);
    }
    @GetMapping(value = "/table-dinner/{tableDinnerId}")
    public ResponseEntity<?> getTableDinnerById(@PathVariable int tableDinnerId){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        TableDinner tableDinner = tableDinnerService.findById(tableDinnerId);
        if(tableDinner == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found table.");
        }
        Eatery eatery = tableDinner.getEatery();
        if(eatery.getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access this table.");
        }
        return ResponseEntity.ok(tableDinner.toDto());
    }
    @PostMapping(value = "/table-dinner/add")
    public ResponseEntity<?> addCategory(@Valid  @RequestBody TableDinnerDto tableDinnerDto){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int eateryId = tableDinnerDto.getEateryId();
        Eatery eatery = eateryService.findById(eateryId);
        if(eatery==null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found table dinner.");
        }
        if(eatery.getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access this eatery.");
        }
        String description = tableDinnerDto.getDescription();
        TableDinner existedTableDinner = tableDinnerService.findTableDinnerByEateryIdAndAndDescription(eateryId,description);
        if(existedTableDinner!=null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Add fail. This table dinner existed.");
        }
        TableDinner tableDinner = mapperService.toTableDinner(tableDinnerDto);
        tableDinnerService.saveTableDinner(tableDinner);
        tableDinnerDto.setId(tableDinner.getId());
        return ResponseEntity.status(HttpStatus.OK).body(tableDinnerDto);

    }
    @PutMapping(value = "/table-dinner/{tableDinnerId}/update")
    public ResponseEntity<?> updateTableDinner(@Valid @PathVariable int tableDinnerId, @RequestBody TableDinnerDto tableDinnerDto) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        TableDinner tableDinner = tableDinnerService.findById(tableDinnerId);
        if(tableDinner == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found table.");
        }
        if(tableDinner.getEatery().getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access this table.");
        }
        int eateryId = tableDinner.getEatery().getId();
        String description = tableDinnerDto.getDescription();
        TableDinner existedTableDinner = tableDinnerService.findTableDinnerByEateryIdAndAndDescription(eateryId,description);
        if(existedTableDinner!=null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update fail. This table dinner existed.");
        }
        tableDinner.setDescription(description);
        tableDinnerService.saveTableDinner(tableDinner);
        return ResponseEntity.status(HttpStatus.OK).body(tableDinner.toDto());
    }

    @PutMapping(value = "/table-dinner/{tableDinnerId}/update-status")
    public ResponseEntity<?> updateTableDinnerStatus(@Valid @PathVariable int tableDinnerId, @RequestBody TableDinnerDto tableDinnerDto) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        TableDinner tableDinner = tableDinnerService.findById(tableDinnerId);
        if(tableDinner == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found table.");
        }
        if(tableDinner.getEatery().getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access this table.");
        }
        boolean status = tableDinnerDto.isStatus();
        tableDinner.setStatus(!status);
        tableDinnerService.saveTableDinner(tableDinner);
        return ResponseEntity.status(HttpStatus.OK).body(tableDinner.toDto());
    }

    @DeleteMapping(value = "/table-dinner/{tableDinnerId}/delete")
    public ResponseEntity<?> deleteTableDinner(@PathVariable int id, @PathVariable int tableDinnerId){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        TableDinner tableDinner = tableDinnerService.findById(tableDinnerId);
        if(tableDinner == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found table.");
        }
        Eatery eatery = tableDinner.getEatery();
        if(eatery.getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access this table.");
        }
        boolean kt = tableDinnerService.deleteById(tableDinnerId);
        if(kt == false) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Delete table dinner fail.");
        return ResponseEntity.status(HttpStatus.OK).body("Delete table dinner successful.");
    }


}
