package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        //given
        Address address = new Address("test", "test", "test");
        Member member = new Member("name", address);

        //when
        Long saveId = memberService.join(member);

        //then
        Assertions.assertThat(member).isEqualTo(memberRepository.findOneById(saveId));
    }

    @Test
    public void 중복_회원_예외() throws Exception{
        //given
        Address address1 = new Address("test", "test", "test");
        Member member1 = new Member("name", address1);
        Address address2 = new Address("test", "test", "test");
        Member member2 = new Member("name", address2);
        //when
        memberService.join(member1);
        //then
        Assertions.assertThatThrownBy(()-> memberService.join(member2))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
