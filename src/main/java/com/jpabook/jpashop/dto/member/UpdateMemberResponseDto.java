package com.jpabook.jpashop.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateMemberResponseDto {

    private Long id;
    private String name;
}
