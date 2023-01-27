package com.jpabook.jpashop;

import com.jpabook.jpashop.domain.*;
import com.jpabook.jpashop.domain.item.Book;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitDBService initDBService;

    @PostConstruct
    public void init(){
        initDBService.initDB1();
        initDBService.initDB2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitDBService{

        private final EntityManager em;

        public void initDB1(){
            Member member = createMember("userA", "서울", "동네1", "집코드1");
            em.persist(member);

            Book book1 = createBook("JPA1 BOOK", 10_000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 20_000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Order order = Order.createOrder(member, createDelivery(member), orderItem1, orderItem2);
            em.persist(order);
        }

        public void initDB2(){
            Member member = createMember("userB","부산", "동네2", "집코드2");
            em.persist(member);

            Book book1 = createBook("SPRING1 BOOK", 20_000, 200);
            em.persist(book1);

            Book book2 = createBook("SPRING2 BOOK", 40_000, 300);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Order order = Order.createOrder(member, createDelivery(member), orderItem1, orderItem2);
            em.persist(order);
        }

        private static Book createBook(String name, int price, int quantity) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(quantity);
            return book1;
        }

        private static Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private static Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member(name,
                    new Address(city, street, zipcode));
            return member;
        }

    }
}
