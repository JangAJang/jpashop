package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderSearch {

    private String memberName;

    private OrderStatus orderStatus;

}
