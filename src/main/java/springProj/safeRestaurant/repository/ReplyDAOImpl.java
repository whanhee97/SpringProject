package springProj.safeRestaurant.repository;

import org.springframework.stereotype.Repository;
import springProj.safeRestaurant.domain.ReplyVO;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class ReplyDAOImpl implements ReplyDAO {

    private final EntityManager em;

    public ReplyDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(ReplyVO vo) {
        em.persist(vo);
    }

    @Override
    public Optional<ReplyVO> ReplyRead(long rno) {
        ReplyVO vo = em.find(ReplyVO.class,rno);
        return Optional.ofNullable(vo);
    }

    @Override
    public void delete(long rno) {
        ReplyVO vo = em.find(ReplyVO.class,rno);
        em.remove(vo);
    }

    @Override
    public void update(ReplyVO nvo) {
        ReplyVO vo = em.find(ReplyVO.class,nvo.getRno());
        Optional.ofNullable(vo).ifPresent(n->{
            n.setContent(nvo.getContent());
        });
    }

    @Override
    public List<ReplyVO> getReplyList(long bno) {
        List<ReplyVO> resultList = em.createQuery("select r from ReplyVO r where r.bno = ?1")
                .setParameter(1, bno)
                .getResultList();
        return resultList;
    }
}
