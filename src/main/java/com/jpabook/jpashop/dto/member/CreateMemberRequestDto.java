package com.jpabook.jpashop.dto.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateMemberRequestDto {

    @NotEmpty(message = "사용자명은 필수로 입력해야합니다.")
    private String name;

    private String city;

    private String street;

    private String zipcode;
}
