package com.jpabook.jpashop.domain;

import com.jpabook.jpashop.domain.item.Item;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @Column(name = "ORDER_ITEMS_PRICE")
    private int orderPrice;

    @Column(name = "ORDER_ITEM_COUNT")
    private int count;

    // 비즈니스 로직
    public void cancel(){
        getItem().increaseStock(count);
    }

    public long getTotalPrice(){
        return (long) orderPrice * count;
    }
}
