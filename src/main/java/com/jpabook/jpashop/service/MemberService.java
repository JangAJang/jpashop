package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member){
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicatedMember(Member member){
        if(!memberRepository.findByName(member.getName()).isEmpty()){
            throw new IllegalArgumentException("이미 가입된 회원입니다");
        }
    }

    @Transactional(readOnly = true)
    public Member findOne(Long id){
        return memberRepository.findOneById(id);
    }

}
