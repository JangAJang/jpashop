package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    public void testMember() throws Exception{
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