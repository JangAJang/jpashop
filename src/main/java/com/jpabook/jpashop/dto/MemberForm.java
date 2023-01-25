package com.jpabook.jpashop.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MemberForm {

    @NotEmpty(message = "회원 이름을 입력해야합니다.")
    private String name;

    private String city;

    private String street;

    private String zipcode;
}
