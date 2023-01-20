package com.jpabook.jpashop.domain.item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Book  extends Item{

    @Column(name = "BOOK_AUTHOR")
    private String author;

    @Column(name = "BOOK_ISBN")
    private String isbn;
}
