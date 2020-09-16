package springProj.safeRestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springProj.safeRestaurant.domain.FreeBoardVO;
import springProj.safeRestaurant.domain.ReplyVO;
import springProj.safeRestaurant.service.FreeBoardService;
import springProj.safeRestaurant.service.ReplyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FreeBoardController {
    @Autowired
    private final FreeBoardService freeBoardService;
    @Autowired
    private final ReplyService replyService;

    public FreeBoardController(FreeBoardService freeBoardService, ReplyService replyService) {
        this.freeBoardService = freeBoardService;
        this.replyService = replyService;
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

        List<ReplyVO> replyList = replyService.getReplyList(bNo);
        model.addAttribute("replyList",replyList);
        model.addAttribute("on","on"); // on이라는 스트링을 사용하기위해 뷰에 넘겨줌

        if(session.getAttribute("checkReplyUpdate") !=null){
            model.addAttribute("checkReplyUpdate",session.getAttribute("checkReplyUpdate").toString());
            //checkReplyUpdate를 저장해서 뷰에 넘겨주고 세션해제 뷰에서는 이걸로 체크를 하여 댓글 수정창을 띄울지 말지 결정한다.
            session.setAttribute("checkReplyUpdate",null);
        }

        if(session.getAttribute("id") != null){ //로그인 여부
            model.addAttribute("userid",session.getAttribute("id").toString());
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

    @PostMapping("/addReply")
    public String addReply(HttpServletRequest request,HttpSession session){
        if(session.getAttribute("id") != null) {

            String content = request.getParameter("replyContent");
            Long bno = Long.parseLong(request.getParameter("bno"));
            String writer = session.getAttribute("id").toString();

            ReplyVO vo = new ReplyVO();
            vo.setContent(content);
            vo.setBno(bno);
            vo.setWriter(writer);
            replyService.create(vo);

            return "redirect:/boardInfo/" + bno.toString();
        }
        return "/loginPage";
    }

    @GetMapping("/deleteReply/{rno}")
    public String deleteReply(@PathVariable("rno") String rno){
        Long rNo = Long.parseLong(rno);
        ReplyVO vo = replyService.ReplyRead(rNo).orElse(null);
        replyService.delete(rNo);
        return "redirect:/boardInfo/"+vo.getBno();
    }

    @GetMapping("/updateReply/{rno}")
    public String goUpdateReply(@PathVariable("rno") String rno, HttpSession session){
        Long rNo = Long.parseLong(rno);
        ReplyVO vo = replyService.ReplyRead(rNo).orElse(null);
        session.setAttribute("checkReplyUpdate",rno + "on"); //checkReplyUpdate를 (rno)on 으로 세션에 저장하고 넘겨준다
        // 넘겨준걸 받으면 세션값을 따로변수에 저장하고 세션값은 null로 만들어준다. checkReplyUpdate는 뷰에서 체크에서 같으면 textarea를 띄우고 아님 안띄운다.
        return "redirect:/boardInfo/"+vo.getBno();
    }

    @PostMapping("/updateReply/{rno}")
    public String updateReply(@PathVariable("rno") String rno, HttpSession session, HttpServletRequest request){
        Long rNo = Long.parseLong(rno);
        String content = request.getParameter("replyContent");
        replyService.update(rNo,content);
        String bno = request.getParameter("bno");
        return "redirect:/boardInfo/"+bno;
    }
}
