package com.jpabook.jpashop.api;

import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderItem;
import com.jpabook.jpashop.dto.ResultDto;
import com.jpabook.jpashop.dto.order.OrderInfoDto;
import com.jpabook.jpashop.dto.order.SimpleOrderDto;
import com.jpabook.jpashop.repository.OrderRepository;
import com.jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        for(Order order : orders){
            order.getMember().getName();
            order.getDelivery().getAddress();

            order.getOrderItems().stream().forEach(s-> s.getItem().getName());
        }
        return orders;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderInfoDto>  ordersV2(){
        // 주문 하나에 있는 orderItem의 리스트 길이만큼 쿼리가 나간다. 리스트 길이들의 총 합 + 1만큼 쿼리가 나간다.
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        return orders.stream().map(OrderInfoDto::new).collect(Collectors.toList());
    }

    @GetMapping("/api/v3/orders")
    public ResultDto ordersV3(){
        // 한방으로 모든 경우를 만들다보니, Order_ID가 중복되게 나올 수 있다. 데이터가 뻥튀기된다.
        List<Order> orders = orderRepository.findAllWithItem();
        return new ResultDto(orders.stream()
                .map(OrderInfoDto::new)
                .collect(Collectors.toList()));
    }
}
