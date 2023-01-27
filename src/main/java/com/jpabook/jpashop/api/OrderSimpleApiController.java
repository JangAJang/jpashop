package com.jpabook.jpashop.api;

import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.dto.ResultDto;
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
        /*
        ~~ToOne으로 매핑된 엔티티들을 모두 프록시 객체를 생성해서 연관관계의 엔티티를 가져온다.
        ByteBuddyInterceptor로 대신해서 나오게 된다.(= 프록시로 초기화해서 나온다)
        Hibernate5Module를 이용해 호출하면 이렇게 프록시로 불러와야 하는데 그러지 못하는 엔티티들을 null로 처리해버리고
        호출한 엔티티를 반환해준다.
        반대로, configure에서 강제로 로딩시키면 가져올 수 있다.

        제일 중요한 것은, 엔티티 자체가 외부로 나가면 안된다.
        보안상 안전하게, 필요한 데이터만 모아서 dto로 반환시켜주는것이 안전하다.
         */
        return orderRepository.findAllByString(new OrderSearch());
    }

    @GetMapping("/api/v2/simple-orders")
    public ResultDto ordersV2(){
        /*
        이러한 방식도 Lazy로딩으로 인한 과도한 쿼리 발생 문제가 있다.
        N+1 문제
        1. Order N개를 찾는 쿼리 1개
        2. Order 1개당 하나씩 있는 Member를 하나씩 찾는 쿼리 N개
        3. Order 1개당 하나씩 있는 Delivery를 하나씩 찾는 쿼리 N개
         */
        return new ResultDto(orderRepository.findAllByString(new OrderSearch()).stream().map(SimpleOrderDto::new)
                .collect(Collectors.toList()));
    }

    // N+1 문제를 해결하기 위한 페치 조인 쿼리 구현
    @GetMapping("/api/v3/simple-orders")
    public ResultDto ordersV3(){
        return new ResultDto(orderRepository.findAllWithMemberDelivery().stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList()));
    }
}
