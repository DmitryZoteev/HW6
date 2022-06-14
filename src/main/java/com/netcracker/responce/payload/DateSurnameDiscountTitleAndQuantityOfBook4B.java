package com.netcracker.responce.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class DateSurnameDiscountTitleAndQuantityOfBook4B {
    private Date date;
    private String surname;
    private byte discount;
    private String titleBook;
    private int quantity;
}
