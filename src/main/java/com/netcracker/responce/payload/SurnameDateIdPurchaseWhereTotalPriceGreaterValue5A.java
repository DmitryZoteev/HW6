package com.netcracker.responce.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class SurnameDateIdPurchaseWhereTotalPriceGreaterValue5A {
    private long purchaseId;
    private String surname;
    private Date date;
}