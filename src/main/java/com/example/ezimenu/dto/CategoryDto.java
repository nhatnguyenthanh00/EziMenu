package com.example.ezimenu.dto;

import com.example.ezimenu.entity.Eatery;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private int id;
    @NotNull
    private Integer eateryId;
    @NotBlank
    private String name;
}
