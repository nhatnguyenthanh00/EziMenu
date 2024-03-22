package com.example.ezimenu.controller.User;

import com.example.ezimenu.dto.NotifyDto;
import com.example.ezimenu.entity.Eatery;
import com.example.ezimenu.entity.Notify;
import com.example.ezimenu.entity.TableDinner;
import com.example.ezimenu.entity.User;
import com.example.ezimenu.service.serviceimpl.EateryService;
import com.example.ezimenu.service.serviceimpl.NotifyService;
import com.example.ezimenu.service.serviceimpl.TableDinnerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class NotifyController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    EateryService eateryService;
    @Autowired
    TableDinnerService tableDinnerService;
    @Autowired
    NotifyService notifyService;
    @GetMapping(value = "/eatery/{id}/notifys")
    public ResponseEntity<?> getAllNotifyByEateryId(@PathVariable int id){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Eatery eatery = eateryService.findById(id);

        if(eatery==null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }
        if(eatery.getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access this eatery.");
        }
        List<Notify> notifyList = notifyService.findAllByEateryId(id);
        List<NotifyDto> notifyDtoList = new ArrayList<>();
        for(Notify notify : notifyList){
            notifyDtoList.add(notify.toDto());
        }
        return ResponseEntity.status(HttpStatus.OK).body(notifyDtoList);
    }
    @GetMapping(value = "/table-dinner/{id}/notifys")
    public ResponseEntity<?> getAllNotifyByTableId(@PathVariable int id){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        TableDinner tableDinner = tableDinnerService.findById(id);

        if(tableDinner==null ){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found table dinner.");
        }
        if(tableDinner.getEatery().getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access this table dinner.");
        }
        List<Notify> notifyList = notifyService.findAllByTableDinnerId(id);
        List<NotifyDto> notifyDtoList = new ArrayList<>();
        for(Notify notify : notifyList){
            notifyDtoList.add(notify.toDto());
        }
        return ResponseEntity.status(HttpStatus.OK).body(notifyDtoList);
    }

    @GetMapping(value = "/notify/{id}/")
    public ResponseEntity<?> getNotifyById(@PathVariable int id){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Notify notify = notifyService.findById(id);
        if(notify == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found notify.");
        }
        if(notify.getTableDinner().getEatery().getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access this notify.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(notify.toDto());
    }
    @PostMapping(value = "/notify/add")
    public ResponseEntity<?> addNotify(@RequestBody NotifyDto notifyDto){
        int tableDinnerId = notifyDto.getTableDinnerId();
        int type = notifyDto.getType();
        String description = notifyDto.getDescription();
        Notify notify = new Notify();
        notify.setTableDinner(tableDinnerService.findById(tableDinnerId));
        notify.setType(type);
        notify.setDescription(description);
        notifyService.saveNotify(notify);
        notifyDto.setId(notify.getId());
        return ResponseEntity.status(HttpStatus.OK).body(notifyDto);
    }

    @DeleteMapping(value ="/notify/{id}/delete")
    public ResponseEntity<?> deleteNotifyById(@PathVariable int id){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Notify notify = notifyService.findById(id);
        if(notify == null || notify.getTableDinner().getEatery().getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access.");
        }
        boolean kt = notifyService.deleteById(id);
        if(kt == false)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Delete fail.");
        return ResponseEntity.status(HttpStatus.OK).body("Delete successful.");
    }
}
