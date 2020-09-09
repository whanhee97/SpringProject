package springProj.safeRestaurant.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import springProj.safeRestaurant.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
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

    @Override
    public void delete(String userId, Long restrNum) {
        Query query = em.createNativeQuery("delete from mypicks  where userid =?1 and restr_num =?2").setParameter(1,userId).setParameter(2,restrNum);
        query.executeUpdate();

    }

    @Override
    public void deleteAll(String userId) {
        Query query = em.createNativeQuery("delete from mypicks  where userid =?1").setParameter(1,userId);
        query.executeUpdate();
    }
}
