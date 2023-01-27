package com.jpabook.jpashop.api;

import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.repository.OrderRepository;
import com.jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> orders(){
        /*
        return orderRepository.findAllByString(new OrderSearch());
        이런식으로 반환을 시키면, Order에서 Member를 찾아와 반환하고, Member에 있는 Order를 다시 반환한다.
        이렇게 무한루프에 빠지게 된다.
        Order와 양방향으로 관계를 갖는 엔티티에 JsonIgnore를 추가해주어야 한다.
         */

    }
}
