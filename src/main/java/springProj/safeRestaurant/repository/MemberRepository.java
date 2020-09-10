package springProj.safeRestaurant.repository;

import springProj.safeRestaurant.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(String id);
    List<Member> findAll();
    void delete(String id);
}
