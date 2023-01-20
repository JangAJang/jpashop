package com.jpabook.jpashop.domain.item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Movie  extends Item{

    @Column(name = "MOVIE_ACTOR")
    private String actor;

    @Column(name = "MOVIE_DIRECTOR")
    private String director;
}
