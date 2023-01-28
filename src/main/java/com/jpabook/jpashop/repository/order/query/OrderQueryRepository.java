package com.jpabook.jpashop.repository.order.query;

import com.jpabook.jpashop.domain.OrderItem;
import com.jpabook.jpashop.dto.order.OrderQueryDto;
import com.jpabook.jpashop.dto.orderItem.OrderItemQueryDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    //이 메서드의 문제, N+1문제가 발생하게 된다. 여기도 최적화를 시켜주어야 한다. 
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

    public List<OrderQueryDto> findAllByDto_Optimazation() {
        List<OrderQueryDto> orders = findOrders();
        List<Long> orderIds = orders.stream().map(OrderQueryDto::getOrderId).collect(Collectors.toList());

        List<OrderItemQueryDto> orderItemQueryDtos = em.createQuery(
                "select new com.jpabook.jpashop.dto.orderItem.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                        "from OrderItem oi " +
                        "join oi.item i " +
                        "where oi.order.id = :orderIds", OrderItemQueryDto.class
        ).setParameter("orderIds", orderIds).getResultList();

        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItemQueryDtos.stream()
                .collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));
        orders.forEach(o-> o.setOrderItemQueryDtos(orderItemMap.get(o)));
        return orders;
        //쿼리 한번에 모든 정보를 가져오고, 가져온 정보들을 주문에 따라 매핑시킨다. 매핑 시킨 컬렉션을 각 주문에 set해준다.
        //쿼리가 두번만 나가게 된다.
    }
}
