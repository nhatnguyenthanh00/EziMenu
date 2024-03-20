package com.example.ezimenu.dto;

import com.example.ezimenu.entity.TableDinner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDto {

    private int id;

    int tableDinnerId;

    private String description;

    private int status = -1;
    // -1 <=> order pending
    //  0 <=> order sent
    //  1 <=> order received
    //  2 <=> order paid

    private int totalPrice = 0 ;
}
