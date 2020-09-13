package springProj.safeRestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import springProj.safeRestaurant.domain.FreeBoardVO;
import springProj.safeRestaurant.service.FreeBoardService;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class FreeBoardController {
    @Autowired
    private final FreeBoardService freeBoardService;

    public FreeBoardController(FreeBoardService freeBoardService) {
        this.freeBoardService = freeBoardService;
    }

    @GetMapping("/boardList")
    public String gotoBoardList(){
        return "/freeBoard/boardList";
    }

    @GetMapping("/newContent")
    public String gotoNewContent(HttpSession session){
        if(session.getAttribute("id") != null){
            return "/freeBoard/createContentForm";
        }
        else{
            return "/loginPage";
        }
    }

    @PostMapping("/createContent")
    public String createContent(ContentForm form, HttpSession session){
        FreeBoardVO vo = new FreeBoardVO();
        vo.setTitle(form.getTitle());
        vo.setContent(form.getContent());
        vo.setWriter(session.getAttribute("id").toString());
        vo.setViewcnt(0);

        freeBoardService.create(vo);
        return "redirect:/boardList";
    }
}
