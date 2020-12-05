package kiwi.blog.member.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kiwi.blog.member.model.request.SaveMemberRequest;
import kiwi.blog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Member", description = "회원")
@RestController
@RequiredArgsConstructor
@RequestMapping("members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping()
    @ApiOperation(value = "회원 저장", notes = "회원을 저장합니다")
    public ResponseEntity<?> saveMember(@RequestBody SaveMemberRequest saveMemberRequest) {

        memberService.saveMember(saveMemberRequest);

        return ResponseEntity.noContent().build();
    }
}
