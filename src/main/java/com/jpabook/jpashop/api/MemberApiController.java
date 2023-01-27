package com.jpabook.jpashop.api;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.dto.ResultDto;
import com.jpabook.jpashop.dto.member.*;
import com.jpabook.jpashop.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v2/members")
    public ResultDto listMembers(){
        return new ResultDto(memberService.findMembers().stream()
                .map(s-> new SearchMemberResponseDto(s.getName()))
                .collect(Collectors.toList()));
    }

    @PostMapping("api/v2/members")
    public CreateMemberResponseDto saveMember(@RequestBody @Valid CreateMemberRequestDto createMemberRequestDto){
        Member member = Member.loadMemberByCreateRequest(createMemberRequestDto);
        memberService.join(member);
        return new CreateMemberResponseDto(member.getId());
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponseDto updateMember(@RequestParam("id")Long id,
                                                @RequestBody @Valid UpdateMemberRequestDto updateMemberRequestDto){
        memberService.update(id, updateMemberRequestDto.getName());
        return new UpdateMemberResponseDto(id, updateMemberRequestDto.getName());
    }
}
