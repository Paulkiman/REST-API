package com.project.book_inventory_api.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "author") // One author can have many books
    private List<Book> books = new ArrayList<>();

    // Getters, setters, constructors
}

