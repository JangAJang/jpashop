package com.jpabook.jpashop.domain.item;

import com.jpabook.jpashop.dto.BookForm;
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

    public static Book createBook(BookForm form){
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        book.setId(form.getId());
        return book;
    }
}
