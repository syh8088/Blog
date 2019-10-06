package kiwi.blog.member.service;

import kiwi.blog.common.utils.BeanUtils;
import kiwi.blog.member.model.entity.Member;
import kiwi.blog.member.model.request.SaveMemberRequest;
import kiwi.blog.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void saveMember(SaveMemberRequest saveMemberRequest) {

        Member member = BeanUtils.copyNullableProperties(saveMemberRequest, Member.class);

        memberRepository.save(member);

    }
}
