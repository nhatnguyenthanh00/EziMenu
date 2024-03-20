package com.example.ezimenu.repository;

import com.example.ezimenu.entity.Notify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface INotifyRepository extends JpaRepository<Notify, Integer> {

    @Query(value = "SELECT n FROM Notify n WHERE n.tableDinner.id =:tableDinnerId ")
    List<Notify> findAllByTableDinnerId(@Param("tableDinnerId") int tableDinnerId);

    @Query(value = "SELECT n FROM Notify n WHERE n.tableDinner.eatery.id =:eateryId")
    List<Notify> findAllByEateryId(@Param("eateryId") int eateryId);
}
