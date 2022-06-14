package com.netcracker.responce.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TitleRepoCountWhereQuantityOfBookLessValue5D {
    private String title;
    private String district;
    private int count;
    private int price;
}
