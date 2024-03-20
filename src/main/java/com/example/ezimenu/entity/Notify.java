package com.example.ezimenu.entity;

import com.example.ezimenu.dto.NotifyDto;
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
@Entity
@Table(name = "notify")
public class Notify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "table_dinner_id")
    TableDinner tableDinner;
    private int type;
    // type = -1 -> Request Confirm Order
    // type =  0 -> Request Payment
    // type =  1 -> Request Support
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss ", timezone = "GMT+7")
    private Date createdAt = Date.from(LocalDateTime.now().atZone(ZoneId.of("GMT+7")).toInstant());;



    public NotifyDto toDto(){
        NotifyDto notifyDto = new NotifyDto();
        notifyDto.setId(id);
        notifyDto.setType(type);
        notifyDto.setDescription(description);
        notifyDto.setTableDinnerId(tableDinner.getId());
        notifyDto.setCreatedAt(createdAt);
        return notifyDto;
    }
}
