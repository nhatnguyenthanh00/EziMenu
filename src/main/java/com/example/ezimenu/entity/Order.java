package com.example.ezimenu.entity;

import com.example.ezimenu.dto.OrderDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "table_dinner_id")
    TableDinner tableDinner;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private int status = -1;

    @Column(name = "total_price")
    private int totalPrice = 0 ;

    public OrderDto toDto(){
        OrderDto orderDto = new OrderDto();
        orderDto.setId(id);
        orderDto.setTableDinnerId(tableDinner.getId());
        orderDto.setDescription(description);
        orderDto.setStatus(status);
        orderDto.setTotalPrice(totalPrice);
        return orderDto;
    }
}
