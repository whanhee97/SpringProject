package springProj.safeRestaurant.repository;

import org.springframework.stereotype.Repository;
import springProj.safeRestaurant.controller.ContentForm;
import springProj.safeRestaurant.domain.FreeBoardVO;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class FreeBoardDAOImpl implements FreeBoardDAO{

    private final EntityManager em;

    public FreeBoardDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(FreeBoardVO vo) {
        em.persist(vo);
    }

    @Override
    public Optional<FreeBoardVO> boardRead(long bno) {
        FreeBoardVO vo = em.find(FreeBoardVO.class,bno);
        return Optional.ofNullable(vo);
    }


    @Override
    public void delete(long bno) {
        FreeBoardVO vo = em.find(FreeBoardVO.class,bno);
        em.remove(vo);
    }

    @Override
    public void update(FreeBoardVO nvo) { // bno 와 제목, 내용을 받아옴
        FreeBoardVO vo = em.find(FreeBoardVO.class,nvo.getBno());
        Optional.ofNullable(vo).ifPresent(n->{
            n.setTitle(nvo.getTitle());
            n.setContent(nvo.getContent());
        });
    }

    @Override
    public List<FreeBoardVO> getBoardList() {
        List<FreeBoardVO> list = em.createQuery("select b from FreeBoardVO b",FreeBoardVO.class).getResultList();
        return list;
    }

    @Override
    public void cntUP(long bno) {
        FreeBoardVO vo = em.find(FreeBoardVO.class,bno);
        long cnt = vo.getViewcnt() + 1;
        Optional.of(vo).ifPresent(n->{
            n.setViewcnt(cnt);
        });
    }

    @Override
    public void replyCntUP(long bno) {
        FreeBoardVO vo = em.find(FreeBoardVO.class,bno);
        long cnt = vo.getReplycnt() + 1;
        Optional.of(vo).ifPresent(n->{
            n.setReplycnt(cnt);
        });
    }
}
