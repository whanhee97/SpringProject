package springProj.safeRestaurant.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springProj.safeRestaurant.domain.FreeBoardVO;
import springProj.safeRestaurant.domain.ReplyVO;
import springProj.safeRestaurant.service.FreeBoardService;
import springProj.safeRestaurant.service.ReplyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/findBoardByKeyword")
    public String findBoardByKeyword(@RequestParam("keyword") String keyword,Model model){
        List<FreeBoardVO> boardList = freeBoardService.getBoardListByKeyword(keyword);
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
    public String createContent(ContentForm form, HttpSession session) throws IOException {
        FreeBoardVO vo = new FreeBoardVO();
        vo.setTitle(form.getTitle());
        vo.setContent(form.getContent());
        vo.setWriter(session.getAttribute("id").toString());
        vo.setViewcnt(0);

        // 파일 업로드 처리
        String fileName = null;
        MultipartFile uploadFile = form.getUploadFile();
        if(!uploadFile.isEmpty()){
            String originalFileName = uploadFile.getOriginalFilename();
            vo.setOriginFileName(originalFileName);
            String ext = FilenameUtils.getExtension(originalFileName); // 이름 중복시 확장처리

            UUID uuid = UUID.randomUUID(); // UUID 구하기 (고유식별자)
            fileName = uuid + "." + ext;
            uploadFile.transferTo(new File("C:\\Users\\HHLee\\study\\upload\\"+fileName));
        }
        /////////////////

        vo.setFileName(fileName);
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

        if(session.getAttribute("checkReplyUpdate") !=null){
            model.addAttribute("checkReplyUpdate",session.getAttribute("checkReplyUpdate").toString());
            //checkReplyUpdate를 저장해서 뷰에 넘겨주고 세션해제 뷰에서는 이걸로 체크를 하여 댓글 수정창을 띄울지 말지 결정한다.
            long rno = Long.parseLong(session.getAttribute("checkReplyUpdate").toString()); // rno받아서
            ReplyVO replyVO = replyService.ReplyRead(rno).orElse(null);// 해당 댓글 찾은 뒤
            String viewContent = replyVO.getContent().replaceAll("<br>","\n"); // <br>을 역변환 시켜주고
            model.addAttribute("viewContent",viewContent);
            session.setAttribute("checkReplyUpdate",null); // 체크상태 해제(체크상태 온이면 수정창 열림)
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
        FreeBoardVO vo = freeBoardService.boardRead(bNo).orElse(null);
        freeBoardService.delete(bNo);

        // 첨부파일 실제 서버에서 삭제
        String fileName = vo.getFileName();
        String path = "C:\\Users\\HHLee\\study\\upload\\" + fileName;

        File file = new File(path);
        if(file.exists()){
            file.delete();
        }

        return "redirect:/";
    }

    @GetMapping("/myBoard/{id}")
    public String gotoMyBoardList(@PathVariable("id") String id,Model model){

        List<FreeBoardVO> boardList = freeBoardService.getBoardListByID(id);
        model.addAttribute("boardList",boardList);
        return "/freeBoard/myBoardList";
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
        session.setAttribute("checkReplyUpdate",rno); //checkReplyUpdate를 rno 으로 세션에 저장하고 넘겨준다
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
