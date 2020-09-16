package springProj.safeRestaurant.repository;

import springProj.safeRestaurant.domain.FreeBoardVO;
import springProj.safeRestaurant.domain.ReplyVO;

import java.util.List;
import java.util.Optional;

public interface ReplyDAO {
    public void create(ReplyVO vo);
    public Optional<ReplyVO> ReplyRead(long rno);
    public void delete(long rno);
    public void update(ReplyVO nvo);
    public List<ReplyVO> getReplyList(long bno);
}
