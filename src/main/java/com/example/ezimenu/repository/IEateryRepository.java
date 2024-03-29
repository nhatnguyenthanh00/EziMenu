package com.example.ezimenu.repository;

import com.example.ezimenu.entity.Eatery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEateryRepository extends JpaRepository<Eatery, Integer> {
    List<Eatery> findAllByUserId(int userId);
    Eatery findById(int id);

    @Query(value = "SELECT e FROM Eatery e WHERE e.id = (SELECT t.eatery.id FROM TableDinner t WHERE t.id = :tableDinnerId)")
    Eatery findByTableDinnerId(int tableDinnerId);
}
