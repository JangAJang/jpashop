package com.jpabook.jpashop.domain;

import com.jpabook.jpashop.exception.delivery.DeliveryAlreadyCompleteException;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.jpabook.jpashop.domain.OrderStatus.CANCEL;

@Entity
@Table(name = "ORDERS")
@Data
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "ORDERS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    // 생성 메서드, 주문 생성에 변경점이 있으면 이 부분만 바꾸면 된다.
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        order.orderItems.addAll(Arrays.asList(orderItems));
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // 비즈니스 로직
    public void cancelOrder(){
        if(delivery.getDeliveryStatus() == DeliveryStatus.COMP){
            throw new DeliveryAlreadyCompleteException("이미 배송 완료된 상품입니다.");
        }
        this.setOrderStatus(CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel(); // 주문 하나에 주문상품이 여러개 있으므로 이를 다 취소해야한다.
        }
    }

    // 조회로직
    public long getTotalPrice(){
        return orderItems.stream()
                .mapToLong(OrderItem::getTotalPrice)
                .sum();
    }
}
