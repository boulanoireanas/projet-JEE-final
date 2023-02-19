package com.example.dataanalyticsservice.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductItem {

    private int quantity;
    private double price;

    private long productID;
    private double discount;
    private String productName;
    //private Bill bill;
}
