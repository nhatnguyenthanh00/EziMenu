package com.example.ezimenu.controller.User;

import com.example.ezimenu.dto.NotifyDto;
import com.example.ezimenu.entity.*;
import com.example.ezimenu.service.serviceimpl.EateryService;
import com.example.ezimenu.service.serviceimpl.NotifyService;
import com.example.ezimenu.service.serviceimpl.OrderService;
import com.example.ezimenu.service.serviceimpl.TableDinnerService;
import com.example.ezimenu.service.transer.MapperService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
    @Autowired
    OrderService orderService;
    @Autowired
    MapperService mapperService;
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
    public ResponseEntity<?> addNotify(@Valid @RequestBody NotifyDto notifyDto){
        int tableDinnerId = notifyDto.getTableDinnerId();
        TableDinner tableDinner = tableDinnerService.findById(tableDinnerId);
        if(tableDinner == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not found table.");
        }
        int type = notifyDto.getType();
        if(type == -1){
            // nếu chưa chọn món --> ko thể gửi notify type = -1
            List<Order> orderPendingList = orderService.findAllByTableDinnerIdAndStatus(tableDinnerId,-1);
            Order orderPending = new Order();
            if(orderPendingList.isEmpty()){
                orderPending.setTableDinner(tableDinner);
                orderService.saveOrder(orderPending);
            }
            else orderPending = orderPendingList.get(0);
            if(orderPending.getOrderItemList().size() == 0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please choose dishes first.");
            }
        }

        if(type == 0){
            // nếu món chưa ra hết --> chưa thể thanh toán -> ko thể gửi notify = 0
            List<Order> orderSentList = orderService.findAllByTableDinnerIdAndStatus(tableDinnerId,0);
            if (orderSentList.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please wait until all dishes served.");
            }
        }

        Notify notify = mapperService.toNotify(notifyDto);
        notifyService.saveNotify(notify);
        notifyDto.setId(notify.getId());
        return ResponseEntity.status(HttpStatus.OK).body(notifyDto);
    }

    @DeleteMapping(value ="/notify/{id}/delete")
    public ResponseEntity<?> deleteNotifyById(@PathVariable int id){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Notify notify = notifyService.findById(id);
        if(notify == null ){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not found notify.");
        }
        if(notify.getTableDinner().getEatery().getUser().getId()!=user.getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have access this notify.");
        }
        boolean kt = notifyService.deleteById(id);
        if(kt == false)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Delete fail.");
        return ResponseEntity.status(HttpStatus.OK).body("Delete successful.");
    }
}
