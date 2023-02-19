package com.example.dataanalyticsservice.models;

import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Long id;
    private String name;
    private double price;
    private double quantity;

}
