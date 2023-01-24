package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderStatus;
import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.exception.item.NotEnoughStockException;
import com.jpabook.jpashop.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
public class OrderServiceTest {


    @Autowired
    private EntityManager em;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("상품 주문시에 성공하면 주문의 상태는 ORDER이다. ")
    public void 상품주문1() throws Exception{
        //given
        Member member = createMember();
        Book book = createBook("시골 JPA", 10_000, 10);

        int orderCount = 2;
        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order order = orderRepository.findOne(orderId);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
    }

    @Test
    @DisplayName("상품 주문시에 성공하면 주문상품 수는 정확하게 저장되어야 한다. ")
    public void 상품주문2() throws Exception{
        //given
        Member member = createMember();
        Book book = createBook("시골 JPA", 10_000, 10);

        int orderCount = 2;
        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order order = orderRepository.findOne(orderId);
        assertThat(order.getOrderItems().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("주문 가격은 가격 * 수량이다 ")
    public void 상품주문3() throws Exception{
        //given
        Member member = createMember();
        Book book = createBook("시골 JPA", 10_000, 10);

        int orderCount = 2;
        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order order = orderRepository.findOne(orderId);
        assertThat(order.getTotalPrice()).isEqualTo(10_000 * orderCount);
    }

    @Test
    @DisplayName("주문시에 주문한 만큼 상품의 재고가 줄어들어야 한다 ")
    public void 상품주문4() throws Exception{
        //given
        Member member = createMember();
        Book book = createBook("시골 JPA", 10_000, 10);

        int orderCount = 2;
        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order order = orderRepository.findOne(orderId);
        assertThat(book.getStockQuantity()).isEqualTo(8);
    }
    
    @Test
    public void 주문취소() throws Exception{
        //given
        
        //when
        
        //then
        
    }
    
    @Test
    public void 상품주문_재고초과() throws Exception{
        //given
        Member member = createMember();
        Book book = createBook("시골 JPA", 10_000, 10);
        //when
        int orderCount = 11;
        //then
        assertThatThrownBy(()->orderService.order(member.getId(), book.getId(), orderCount))
                .isInstanceOf(NotEnoughStockException.class);
        
    }

    public Member createMember(){
        Address address = new Address("test", "test", "test");
        Member member = new Member("name", address);
        em.persist(member);
        return member;
    }

    public Book createBook(String name, int price, int quantity){
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }
}
