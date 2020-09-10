package springProj.safeRestaurant.repository;

import org.springframework.stereotype.Repository;
import springProj.safeRestaurant.domain.Member;
import springProj.safeRestaurant.repository.MemberRepository;

import java.util.*;
//@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static Map<String,Member> store = new HashMap<>();

    @Override
    public Member save(Member member) {
        store.put(member.getId(),member);
        return null;
    }

    @Override
    public Optional<Member> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(String id) {

    }

    public void clearStore(){
        store.clear();
    }
}
