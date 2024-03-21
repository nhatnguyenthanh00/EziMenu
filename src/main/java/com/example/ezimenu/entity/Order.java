package com.example.ezimenu.entity;

import com.example.ezimenu.dto.OrderDto;
import com.example.ezimenu.dto.OrderItemDto;
import jakarta.persistence.*;
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
    // -1 <=> order pending
    //  0 <=> order sent
    //  1 <=> order received
    //  2 <=> order paid

    @Column(name = "total_price")
    private int totalPrice = 0 ;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItemList = new ArrayList<>();

    public OrderDto toDto(){
        OrderDto orderDto = new OrderDto();
        orderDto.setId(id);
        orderDto.setTableDinnerId(tableDinner.getId());
        orderDto.setDescription(description);
        orderDto.setStatus(status);
        orderDto.setTotalPrice(totalPrice);
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        for(OrderItem orderItem : this.orderItemList){
            orderItemDtoList.add(orderItem.toDto());
        }
        orderDto.setOrderItemDtoList(orderItemDtoList);
        return orderDto;
    }
}
