package com.jpabook.jpashop.domain;

import com.jpabook.jpashop.dto.member.CreateMemberRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "MEMBER_NAME")
    @NotEmpty
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> myOrders = new ArrayList<>();

    public Member(String name, Address address){
        this.name = name;
        this.address = address;
    }

    public static Member loadMemberByCreateRequest(CreateMemberRequestDto createMemberRequestDto){
        return new Member(createMemberRequestDto.getName(),
                new Address(createMemberRequestDto.getCity(),
                        createMemberRequestDto.getStreet(),
                        createMemberRequestDto.getZipcode()));

    }
}
