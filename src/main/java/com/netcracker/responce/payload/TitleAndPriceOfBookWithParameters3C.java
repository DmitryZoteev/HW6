package com.netcracker.responce.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TitleAndPriceOfBookWithParameters3C {
    private String title;
    private int price;
}
