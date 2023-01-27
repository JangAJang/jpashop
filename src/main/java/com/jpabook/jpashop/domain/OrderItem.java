package com.jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Order order;

    @Column(name = "ORDER_ITEMS_PRICE")
    private int orderPrice;

    @Column(name = "ORDER_ITEM_COUNT")
    private int count;

    //생성 메서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.decreaseStock(count);
        return orderItem;
    }

    // 비즈니스 로직
    public void cancel(){
        getItem().increaseStock(count);
    }

    //조회 로직
    public long getTotalPrice(){
        return (long) orderPrice * count;
    }
}
