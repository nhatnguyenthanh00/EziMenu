package com.example.ezimenu.repository;

import com.example.ezimenu.entity.Eatery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEateryRepository extends JpaRepository<Eatery, Integer> {
    List<Eatery> findAllByUserId(int userid);

    Eatery findById(int id);
}
