package com.jpabook.jpashop.domain.item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Album extends Item{

    @Column(name = "ALBUM_ARTIST")
    private String artist;

    @Column(name = "ALBUM_ETC")
    private String etc;
}
