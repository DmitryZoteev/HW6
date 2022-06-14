package com.netcracker.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "books")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long id;
    @Column(name = "book_title")
    private String title;
    @Column(name = "book_price")
    private int price;
    @Column(name = "book_repo")
    private String repo;
    @Column(name = "book_quantity")
    private int quantity;
}
