package springProj.safeRestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springProj.safeRestaurant.domain.FreeBoardVO;
import springProj.safeRestaurant.service.FreeBoardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class FreeBoardController {
    @Autowired
    private final FreeBoardService freeBoardService;

    public FreeBoardController(FreeBoardService freeBoardService) {
        this.freeBoardService = freeBoardService;
    }

    @GetMapping("/boardList")
    public String gotoBoardList(Model model){
        List<FreeBoardVO> boardList = freeBoardService.getBoardList();
        model.addAttribute("boardList",boardList);
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

    @GetMapping("/boardInfo/{bno}")
    public String boardInfo(Model model,HttpSession session, @PathVariable("bno") String bno){

        Long bNo = Long.parseLong(bno);
        FreeBoardVO vo = freeBoardService.boardRead(bNo).orElse(null);
        freeBoardService.cntUP(bNo);
        model.addAttribute("boardInfo",vo);

        if(session.getAttribute("id") != null){ //로그인 여부
            if (session.getAttribute("id").toString().equals(vo.getWriter())){ //작성자와 로그인 되어있는 아이디 같은지
                return "/freeBoard/loggedBoardInfo";
            }
            else{
                return "/freeBoard/boardInfo";
            }
        }
        return "/freeBoard/boardInfo";
    }

    @GetMapping("/updateBoard")
    public String gotoUpdateBoard(@RequestParam("bno") String bno, Model model){
        Long bNo = Long.parseLong(bno);
        FreeBoardVO vo = freeBoardService.boardRead(bNo).orElse(null);
        String title = vo.getTitle();
        String content = vo.getContent();

        //역변환
        title = title.replace("&lt;","<");
        title = title.replace("&gt;",">");
        title = title.replace("&nbsp;&nbsp;"," ");
        content = content.replace("<br>","\n");

        model.addAttribute("title",title);
        model.addAttribute("content",content);
        model.addAttribute("boardInfo",vo);
        return "/freeBoard/updateBoard";
    }

    @PostMapping("/updateBoard")
    public String updateBoard(ContentForm form, HttpServletRequest request){
        Long bNo = Long.parseLong(request.getParameter("bno"));
        freeBoardService.update(bNo,form);
        return "redirect:/boardList";
    }

    @GetMapping("/deleteBoard")
    public String deleteBoard(@RequestParam("bno") String bno){
        Long bNo = Long.parseLong(bno);
        freeBoardService.delete(bNo);
        return "redirect:/";
    }
}
