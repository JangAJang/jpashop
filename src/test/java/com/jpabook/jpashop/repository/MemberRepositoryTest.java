package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    public void testMember(){
        //given
        Member member = new Member();
        member.setName("name1");
        Long savedId = memberRepository.save(member);

        //when
        Member findMember = memberRepository.findById(savedId);

        //then
        Assertions.assertThat(findMember.getName()).isEqualTo("name1");
        Assertions.assertThat(findMember.getId()).isEqualTo(savedId);

    }
}