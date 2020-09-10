package springProj.safeRestaurant.repository;

import org.springframework.stereotype.Repository;
import springProj.safeRestaurant.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepositoryImpl implements MemberRepository{

    private final EntityManager em;

    public MemberRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(String id) {
        Member member = em.find(Member.class,id);
        return Optional.ofNullable(member);
    }

    @Override
    public List<Member> findAll() {
        List<Member> list = em.createQuery("select m from Member m",Member.class).getResultList();
        return list;
    }

    @Override
    public void delete(String id){
        Member member = em.find(Member.class,id);
        em.remove(member);
    }
}
