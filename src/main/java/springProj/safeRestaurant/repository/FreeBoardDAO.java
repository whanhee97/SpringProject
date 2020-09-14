package springProj.safeRestaurant.repository;

import springProj.safeRestaurant.controller.ContentForm;
import springProj.safeRestaurant.domain.FreeBoardVO;

import java.util.List;
import java.util.Optional;

public interface FreeBoardDAO {
    public void create(FreeBoardVO vo);
    public Optional<FreeBoardVO> boardRead(long bno);
    public void delete(long bno);
    public void update(FreeBoardVO nvo);
    public List<FreeBoardVO> getBoardList();
    public void cntUP(long bno); // 조회수 증가
}
