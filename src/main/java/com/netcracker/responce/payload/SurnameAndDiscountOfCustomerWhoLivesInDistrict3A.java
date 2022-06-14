package com.netcracker.responce.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SurnameAndDiscountOfCustomerWhoLivesInDistrict3A {
    private String surname;
    private byte discount;
}
