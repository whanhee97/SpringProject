package springProj.safeRestaurant.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springProj.safeRestaurant.controller.ContentForm;
import springProj.safeRestaurant.domain.FreeBoardVO;
import springProj.safeRestaurant.repository.FreeBoardDAO;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class FreeBoardService {

    private final FreeBoardDAO freeBoardDAO;

    public FreeBoardService(FreeBoardDAO freeBoardDAO) {
        this.freeBoardDAO = freeBoardDAO;
    }

    public void create(FreeBoardVO vo){
        charProcess(vo);

        freeBoardDAO.create(vo);
    }


    public Optional<FreeBoardVO> boardRead(long bno){
        return freeBoardDAO.boardRead(bno);
    }

    public void delete(long bno){
        freeBoardDAO.delete(bno);
    }

    public void update(Long bno, ContentForm form){
        FreeBoardVO nvo = freeBoardDAO.boardRead(bno).orElse(null);
        nvo.setTitle(form.getTitle());
        nvo.setContent(form.getContent());
        charProcess(nvo);

        freeBoardDAO.update(nvo);
    }

    public List<FreeBoardVO> getBoardList(){
        return freeBoardDAO.getBoardList();
    }

    public void cntUP(long bno){
        freeBoardDAO.cntUP(bno);
    } // 조회수 증가

    private void charProcess(FreeBoardVO vo) {
        String title = vo.getTitle();
        String content = vo.getContent();

        // 태그문자 처리 ('<' -> &lt; , '>' -> &gt;)
        // replace(A,B) A를 B로 변경
        title = title.replace("<","&lt;");
        title = title.replace(">","&gt;");

        // 공백문자 처리
        title = title.replace(" ","&nbsp;&nbsp;");

        // 줄바꿈 문자처리
        content = content.replace("\n","<br>");

        vo.setTitle(title);
        vo.setContent(content);
    }
}
