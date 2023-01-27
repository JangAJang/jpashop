package com.jpabook.jpashop.api;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.dto.member.CreateMemberRequestDto;
import com.jpabook.jpashop.dto.member.CreateMemberResponseDto;
import com.jpabook.jpashop.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("api/v2/members")
    public CreateMemberResponseDto saveMember(@RequestBody @Valid CreateMemberRequestDto createMemberRequestDto){
        Member member = Member.loadMemberByCreateRequest(createMemberRequestDto);
        memberService.join(member);
        return new CreateMemberResponseDto(member.getId());
    }
}
