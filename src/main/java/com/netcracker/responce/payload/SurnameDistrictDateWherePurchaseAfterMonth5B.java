package com.netcracker.responce.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class SurnameDistrictDateWherePurchaseAfterMonth5B {
    private String surname;
    private String district;
    private Date date;
}
