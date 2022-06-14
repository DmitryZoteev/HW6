package com.netcracker.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private long id;
    @Column(name = "customer_surname")
    private String surname;
    @Column(name = "customer_district")
    private String district;
    @Column(name = "customer_discount")
    private byte discount;
}
