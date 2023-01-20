package com.jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    @Column(name = "ITEM_NAME")
    private String name;

    @Column(name = "ITEM_PRICE")
    private int price;

    @Column(name = "ITEM_STOCK_QUANTITY")
    private int stockQuantity;
}
