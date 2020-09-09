package springProj.safeRestaurant.repository;

import org.springframework.stereotype.Repository;
import springProj.safeRestaurant.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class MyPicksDAOImpl implements MyPicksDAO{
    private final EntityManager em;

    public MyPicksDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void insert(MyPicksDTO dto) {
        em.persist(dto);
    }

    @Override
    public List<MyPicksDTO> findByUserId(String userId) {
        List<MyPicksDTO> list = em.createQuery("select m from MyPicksDTO m where m.userId = :userId", MyPicksDTO.class)
                .setParameter("userId",userId)
                .getResultList();
        return list;
    }
/*
    @Override
    public void delete(Long restrNum) {
        em.
    }

    @Override
    public void deleteAll(String userId) {

    }

 */
}
