package com.example.ezimenu.entity;

import com.example.ezimenu.dto.NotifyDto;
import com.example.ezimenu.dto.OrderDto;
import com.example.ezimenu.dto.TableDinnerDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "table_dinner")
public class TableDinner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "eatery_id")
    private Eatery eatery;

    @Column(name = "status")
    private boolean status = false;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "tableDinner", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Order> orderList;

    @OneToMany(mappedBy = "tableDinner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notify> notifyList;

    public String createSuperStatus(){
        if(status == false) return "Bàn trống";
        List<Order> orderSentList = new ArrayList<>();
        for(Order order : orderList){
            if(order.getStatus()==0) orderSentList.add(order);
        }
        if(orderSentList.isEmpty()) return "Đang gọi món";
        return  "Đang chờ món";
    }

    public TableDinnerDto toDto(){
        TableDinnerDto tableDinnerDto = new TableDinnerDto();
        tableDinnerDto.setId(id);
        tableDinnerDto.setEateryId(eatery.getId());
        tableDinnerDto.setStatus(status);
        tableDinnerDto.setDescription(description);
        List<OrderDto> orderDtoList = new ArrayList<>();
        for(Order order : orderList){
            orderDtoList.add(order.toDto());
        }
        tableDinnerDto.setOrderDtoList(orderDtoList);
        List<NotifyDto> notifyDtoList = new ArrayList<>();
        for(Notify notify : notifyList){
            notifyDtoList.add(notify.toDto());
        }
        tableDinnerDto.setNotifyDtoList(notifyDtoList);
        return tableDinnerDto;
    }
}
