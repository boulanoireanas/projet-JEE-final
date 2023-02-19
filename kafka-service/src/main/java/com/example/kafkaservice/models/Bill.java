package com.example.kafkaservice.models;

import com.example.kafkaservice.enums.OrderStatus;
import lombok.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Bill {

    private Date billingDate;

    private OrderStatus status;

    private List<ProductItem> productItems;

    private Long customerid;

    private Customer customer;

}
