package kiwi.blog.common.config.handler;

import kiwi.blog.common.config.authentication.AppUserDetails;
import kiwi.blog.member.model.entity.Member;
import kiwi.blog.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserServiceHandler implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    public UserServiceHandler(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByIdAndUseYn(username, true);

        if (member == null) {
            throw new UsernameNotFoundException("unsername not found");
        }

        //List<GrantedAuthority> grants = member.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        List<SimpleGrantedAuthority> grants = member.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());


        AppUserDetails userDetail = new AppUserDetails();

        userDetail.setUsername(member.getId());
        userDetail.setPassword(member.getPassword());
        userDetail.setId(member.getMemberNo());
        userDetail.setName(member.getName());

        userDetail.setAuthorities(grants);
        return userDetail;
        // return new User(member.getId(), member.getPassword(), grants);
    }
}
