package com.example.ezimenu.repository;

import com.example.ezimenu.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderItemRepository extends JpaRepository<OrderItem, Integer> {
    OrderItem findById(int id);

    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.id = :orderId")
    List<OrderItem> findAllByOrderId(@Param("orderId") int orderId);

    @Query(value = "SELECT o FROM OrderItem o WHERE o.order.id = :orderId AND o.dish.id = :dishId")
    OrderItem findByOrderIdAndDishId(@Param("orderId") int orderId, @Param("dishId") int dishId);

}
