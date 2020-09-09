package springProj.safeRestaurant.repository;

import springProj.safeRestaurant.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("HH");
        member.setAge(24);
        member.setAddress("AnYang");
        member.setGender("Male");
        member.setId("whanhee97");
        member.setPw("1234");

        repository.save(member);

        Member result = repository.findById("whanhee97").orElse(null);
        System.out.println(result.getGender());
    }

}
