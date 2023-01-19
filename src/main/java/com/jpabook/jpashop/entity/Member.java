package com.jpabook.jpashop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "MEMBER_NAME")
    private String name;

}
