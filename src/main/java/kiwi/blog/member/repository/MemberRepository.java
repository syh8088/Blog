package kiwi.blog.member.repository;

import kiwi.blog.member.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByIdAndUseYn(String id, boolean useYn);
}
