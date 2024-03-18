package com.example.ezimenu.dto;

import com.example.ezimenu.entity.Eatery;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TableDinnerDto {
    private int id;

    private int eateryId;
    private boolean status = true;
    private String description;
}