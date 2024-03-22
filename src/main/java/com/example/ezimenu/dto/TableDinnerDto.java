package com.example.ezimenu.dto;

import com.example.ezimenu.entity.Eatery;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class TableDinnerDto {
    private int id;
    @NotNull
    private Integer eateryId;
    private boolean status = true;
    private String description;
    private List<OrderDto> orderDtoList = new ArrayList<>();
    private List<NotifyDto> notifyDtoList = new ArrayList<>();
}
