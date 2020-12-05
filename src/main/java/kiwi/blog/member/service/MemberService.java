package kiwi.blog.member.service;

import kiwi.blog.common.utils.BeanUtils;
import kiwi.blog.member.model.entity.Member;
import kiwi.blog.member.model.request.SaveMemberRequest;
import kiwi.blog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void saveMember(SaveMemberRequest saveMemberRequest) {

        Member member = BeanUtils.copyNullableProperties(saveMemberRequest, Member.class);

        memberRepository.save(member);

    }
}
