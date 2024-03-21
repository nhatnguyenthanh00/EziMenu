package com.example.ezimenu.repository;

import com.example.ezimenu.entity.Dish;
import com.example.ezimenu.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {

    Order findById(int id);
    @Query("SELECT o FROM Order o JOIN o.tableDinner t JOIN t.eatery e WHERE e.id = :eateryId")
    List<Order> findAllByEateryId(@Param("eateryId") int eateryId);

    @Query("SELECT o FROM Order o JOIN o.tableDinner t WHERE t.id = :tableDinnerId")
    List<Order> findAllByTableDinnerId(@Param("tableDinnerId") int tableDinnerId);

    @Query(value = "SELECT o FROM Order o WHERE o.tableDinner.id =:tableDinnerId AND o.status= :status")
    List<Order> findAllByTableDinnerIdAndStatus(@Param("tableDinnerId")int tableDinnerId, @Param("status")int status);
}
