package com.example.ezimenu.dto;

import com.example.ezimenu.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EateryDto {
    private int id;
    private int userId;
    private String address;
    private String description;

}
