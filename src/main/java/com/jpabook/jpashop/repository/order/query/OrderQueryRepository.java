package com.jpabook.jpashop.repository.order.query;

import com.jpabook.jpashop.domain.OrderItem;
import com.jpabook.jpashop.dto.order.OrderQueryDto;
import com.jpabook.jpashop.dto.orderItem.OrderItemQueryDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    private List<OrderQueryDto> findOrders(){
        return em.createQuery(
                "select new com.jpabook.jpashop.dto.order.OrderQueryDto(o.id, m.name, o.orderDate, o.orderStatus, d.address) " +
                        " from Order o " +
                        "join o.member m " +
                        "join o.delivery d ", OrderQueryDto.class
        ).getResultList();
    }

    public List<OrderQueryDto> findOrderQueryDto(){
        List<OrderQueryDto> orders = findOrders();
        orders.forEach(o ->{
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItemQueryDtos(orderItems);
        });
        return orders;
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId){
        return em.createQuery(
                "select new com.jpabook.jpashop.dto.orderItem.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                        "from OrderItem oi " +
                        "join oi.item i " +
                        "where oi.order.id = :orderId", OrderItemQueryDto.class
        ).setParameter("orderId", orderId).getResultList();
    }
}