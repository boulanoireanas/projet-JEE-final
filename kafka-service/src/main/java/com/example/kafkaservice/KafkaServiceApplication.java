package com.example.kafkaservice;

import com.example.kafkaservice.enums.OrderStatus;
import com.example.kafkaservice.models.Bill;
import com.example.kafkaservice.models.Customer;
import com.example.kafkaservice.models.Product;
import com.example.kafkaservice.models.ProductItem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SpringBootApplication
public class KafkaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaServiceApplication.class, args);
    }

    @Bean
    public Supplier<Bill> billSupplier() {
        return () -> {

            List<Product> products = new ArrayList<>();
            products.add(new Product(4l, "Aspirateur", 758, 4));
            products.add(new Product(5l, "Chauffe-eau électrique", 1588, 16));
            products.add(new Product(6l, "Lisseur", 499, 3));

            List<Customer> customers = new ArrayList<>();
            customers.add(new Customer(4L, "Rita", "rita@gmail.com"));
            customers.add(new Customer(5L, "Kenza", "kenza@gmail.com"));
            customers.add(new Customer(6L, "Jad", "Jad@gmail.com"));

            Random random = new Random();
            Customer cu=customers.get(random.nextInt(customers.size()));
            Bill order = Bill.builder()
                    .customerid(cu.getId())
                    .customer(cu)
                    .status(Math.random() > 0.5 ? OrderStatus.PENDING : OrderStatus.CREATED)
                    .billingDate(new Date())
                    .build();
            List<ProductItem> prods=new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                    ProductItem productItem = ProductItem.builder()
                           // .bill(order) // prob de boucle
                            .productID(products.get(j).getId())
                            .productName(products.get(j).getName())
                            .price(products.get(j).getPrice())
                            .quantity(1 + random.nextInt(10))
                            .discount(Math.random())
                            .build();
                    System.out.println(j+" : "+productItem.getProductName());

                    prods.add(productItem);
            }
            //int s=prods.size();
            //System.out.println("size : "+s);
            order.setProductItems(prods);
            return order;
        };
    }

}
