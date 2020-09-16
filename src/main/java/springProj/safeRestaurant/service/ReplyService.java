package springProj.safeRestaurant.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springProj.safeRestaurant.domain.ReplyVO;
import springProj.safeRestaurant.repository.FreeBoardDAO;
import springProj.safeRestaurant.repository.ReplyDAO;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ReplyService {
    private final ReplyDAO replyDAO;
    private final FreeBoardDAO freeBoardDAO;

    public ReplyService(ReplyDAO replyDAO, FreeBoardDAO freeBoardDAO) {
        this.replyDAO = replyDAO;
        this.freeBoardDAO = freeBoardDAO;
    }

    public void create(ReplyVO vo){
        charProcess(vo);
        freeBoardDAO.replyCntUP(vo.getBno());
        replyDAO.create(vo);
    }
    public Optional<ReplyVO> ReplyRead(long rno){
        return replyDAO.ReplyRead(rno);
    }
    public void delete(long rno){
        ReplyVO vo = replyDAO.ReplyRead(rno).orElse(null);
        freeBoardDAO.replyCntDown(vo.getBno());
        replyDAO.delete(rno);
    }
    public void update(Long rno,String newContent){
        ReplyVO nvo = replyDAO.ReplyRead(rno).orElse(null);
        nvo.setContent(newContent);
        charProcess(nvo);

        replyDAO.update(nvo);
    }
    public List<ReplyVO> getReplyList(long bno){
        return replyDAO.getReplyList(bno);
    }


    private void charProcess(ReplyVO vo) {
        String content = vo.getContent();
        // 줄바꿈 문자처리
        content = content.replace("\n","<br>");

        vo.setContent(content);
    }
}
