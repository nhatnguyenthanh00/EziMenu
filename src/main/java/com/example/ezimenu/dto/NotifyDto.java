package com.example.ezimenu.dto;

import com.example.ezimenu.entity.TableDinner;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotifyDto {

    private int id;
    @NotNull
    private int tableDinnerId;
    @NotNull
    private Integer type;
    // type = -1 -> Request Confirm Order
    // type =  0 -> Request Payment
    // type =  1 -> Request Support
    @NotNull
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss ", timezone = "GMT+7")
    private Date createdAt = Date.from(LocalDateTime.now().atZone(ZoneId.of("GMT+7")).toInstant());
}
