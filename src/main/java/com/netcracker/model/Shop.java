package com.netcracker.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "shops")
@Data
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private long id;
    @Column(name = "shop_name")
    private String name;
    @Column(name = "shop_district")
    private String district;
    @Column(name = "shop_commission")
    private byte commission;
}