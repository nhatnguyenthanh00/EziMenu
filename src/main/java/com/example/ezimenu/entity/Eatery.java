package com.example.ezimenu.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Eatery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "address")
    private String address;
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "eatery",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categoryList;

    @OneToMany(mappedBy = "eatery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TableDinner> tableDinnerList;

}
