package com.project.book_inventory_api.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String contact;

    @ManyToMany // One author can have many books
    private Set<Book> books=new HashSet<Book>();

    // Getters, setters, constructors
}

