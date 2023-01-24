package com.jpabook.jpashop.domain.item;

import com.jpabook.jpashop.domain.Category;
import com.jpabook.jpashop.exception.item.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    @Column(name = "ITEM_NAME")
    private String name;

    @Column(name = "ITEM_PRICE")
    private int price;

    @Column(name = "ITEM_STOCK_QUANTITY")
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // ======비즈니스 로직======
    public void increaseStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void decreaseStock(int quantity){
        if(quantity > this.stockQuantity)
            throw new NotEnoughStockException("재고를 0보다 작게 줄일 수 없습니다. 현재 수량 : " + this.stockQuantity);
        this.stockQuantity -= quantity;
    }
}
