package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.item.Item;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void saveItem(Item item){
        if(item.getId() == null){
            em.persist(item);
            return;
        }
        em.merge(item);
    }

    public Item findOneById(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

    public List<Item> findByName(String name){
        return em.createQuery("select i from Item i where i.name = :name", Item.class)
                .setParameter("name", name)
                .getResultList();
    }
}
