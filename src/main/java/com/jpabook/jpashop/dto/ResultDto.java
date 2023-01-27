package com.jpabook.jpashop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResultDto<T> {

    private T data;
}
