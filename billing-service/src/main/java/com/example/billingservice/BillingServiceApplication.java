package com.example.billingservice;

import com.example.billingservice.entities.Bill;
import com.example.billingservice.entities.ProductItem;
import com.example.billingservice.enums.OrderStatus;
import com.example.billingservice.model.Customer;
import com.example.billingservice.repository.BillRepository;
import com.example.billingservice.repository.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.*;
import java.util.function.Consumer;

@EnableFeignClients
@SpringBootApplication
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    BillRepository orderRepo;
    ProductItemRepository productItemRepo;

    public BillingServiceApplication(BillRepository orderRepo, ProductItemRepository productItemRepo) {
        this.orderRepo = orderRepo;
        this.productItemRepo = productItemRepo;
    }

    //@Bean
    CommandLineRunner start(
            BillRepository orderRepository,
            ProductItemRepository productItemRepository
    ){
        return args -> {

            List<com.example.billingservice.model.Product> products= new ArrayList<>();
            products.add(new com.example.billingservice.model.Product(1l,"Ordinateur",88,12));
            products.add(new com.example.billingservice.model.Product(2l,"Imprimante",88,15));
            products.add(new com.example.billingservice.model.Product(3l,"Smartphone",1288,55));

            List<Customer> customers= new ArrayList<>();
            customers.add(new Customer(1L,"Imane","imi@gmail.com"));
            customers.add(new Customer(2L,"Touria","tou@gmail.com"));
            customers.add(new Customer(3L,"Amine","amine@gmail.com"));
            Random random=new Random();
            for (int i = 0; i <20 ; i++) {
                System.out.println(customers.get(random.nextInt(customers.size())));
                Bill order=Bill.builder()
                        .customerid(customers.get(random.nextInt(customers.size())).getId())
                        .status(Math.random()>0.5? OrderStatus.PENDING:OrderStatus.CREATED)
                        .billingDate(new Date())
                        .build();
                Bill savedOrder = orderRepository.save(order);
                for (int j = 0; j < 3 ; j++) {
                    if(Math.random()>0.70){
                        ProductItem productItem= ProductItem.builder()
                                .bill(savedOrder)
                                .productID(products.get(j).getId())
                                .price(products.get(j).getPrice())
                                .quantity(1+random.nextInt(10))
                                .discount(Math.random())
                                .build();
                        productItemRepository.save(productItem);
                    }

                }
            }
        };
    }

    @Bean
    public Consumer<Bill> billConsumer() {
        System.out.println("called");
        return (input) -> {

            System.out.println("****************");
            System.out.println(input.toString());
            System.out.println("****************");
            Bill order=Bill.builder()
                    .customerid(input.getCustomer().getId())
                    .customer(input.getCustomer())
                    .status(input.getStatus())
                    .billingDate(input.getBillingDate())
                    .build();
            Bill savedOrder = orderRepo.save(order);
            input.getProductItems().forEach(
                    (i)->{productItemRepo.save(
                            ProductItem.builder()
                                    .bill(savedOrder)
                                    .productID(i.getProductID())
                                    .price(i.getPrice())
                                    .quantity(i.getQuantity())
                                    .discount(i.getDiscount())
                                    .build()
                    );}
            );
        };
    }
}
