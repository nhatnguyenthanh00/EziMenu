package com.example.ezimenu.entity;

import com.example.ezimenu.dto.TableDinnerDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public TableDinnerDto toDto(){
        TableDinnerDto tableDinnerDto = new TableDinnerDto();
        tableDinnerDto.setId(id);
        tableDinnerDto.setEateryId(eatery.getId());
        tableDinnerDto.setStatus(status);
        tableDinnerDto.setDescription(description);
        return tableDinnerDto;
    }
}
