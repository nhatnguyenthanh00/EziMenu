package com.example.ezimenu.dto;

import com.example.ezimenu.entity.TableDinner;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private int tableDinnerId;
    private int type;
    // type = -1 -> Request Confirm Order
    // type =  0 -> Request Payment
    // type =  1 -> Request Support
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss ", timezone = "GMT+7")
    private Date createdAt = Date.from(LocalDateTime.now().atZone(ZoneId.of("GMT+7")).toInstant());
}
