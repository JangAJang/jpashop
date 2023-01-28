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
import org.springframework.web.bind.annotation.RequestParam;
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
        // fetch join으로 쿼리 하나로 모든 것을 조회하고, 추가적인 쿼리가 발생하지 않는다.
        // 한방으로 모든 경우를 만들다보니, Order_ID가 중복되게 나올 수 있다. 데이터가 뻥튀기된다.
        // 그래서 distinct를 사용하면, 중복을 제거해준다.
        // 문제는, 데이터가 많아진 상태에서 페이징 처리를 할 수 없다. limit, offset이 쿼리로 발생되지 않는다.
        List<Order> orders = orderRepository.findAllWithItem();
        return new ResultDto(orders.stream()
                .map(OrderInfoDto::new)
                .collect(Collectors.toList()));
    }

    /*
    컬렉션 페이징
    ~ToOne관계를 모두 fetch join한다. (row가 증가하지 않는다)
    컬렉션은 지연로딩시킨다.
    지연로딩을 성능 최적화시키기 위해 @BatchSize어노테이션을 엔티티에 등록시킨다.
    이전 V3는 쿼리가 한방에 처리되지만, 중복된 데이터가 과하게 생성된다. 이게 전부 DB에서 전송되기에, 전송량이 늘어난다.
    v3.1은
     */
    @GetMapping("/ap/v3.1/orders")
    public ResultDto ordersV3_1(@RequestParam(value = "offset", defaultValue = "0")int offset,
                                @RequestParam(value = "count", defaultValue = "100")int count){
        //1. ~대일 관계를 모두 페치조인시킨다.
        List<OrderInfoDto> result = orderRepository.findAllWithMemberDelivery(offset, count)
                .stream().map(OrderInfoDto::new).collect(Collectors.toList());
        return new ResultDto(result);
    }
}
