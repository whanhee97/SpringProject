package springProj.safeRestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import springProj.safeRestaurant.domain.Member;
import springProj.safeRestaurant.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @PostMapping("/members/new")
    public String join(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());
        member.setAge(form.getAge());
        member.setGender(form.getGender());
        member.setAddress(form.getAddress());
        member.setId(form.getId());
        member.setPw(form.getPw());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/memberInfo")
    public String memberInfo(HttpSession session, Model model){
        String id = session.getAttribute("id").toString();
        Member member = memberService.findById(id);
        model.addAttribute("member",member);
        return "/members/showInfo";
    }

    @GetMapping("/insertInfo")
    public String goInsertInfo(HttpSession session,Model model){
        Member member = memberService.findById(session.getAttribute("id").toString());
        model.addAttribute("member",member);
        return "/members/insertInfo";
    }

    @PostMapping("/modifyInfo")
    public String modifyInfo(infoForm infoForm, HttpSession session){
        String id = session.getAttribute("id").toString();
        memberService.updateMemberInfo(id,infoForm);

        return "redirect:/memberInfo";
    }

    @GetMapping("/deleteMemberPWC")
    public String deleteMemberPWC(){
        return "/members/pwChecking";
    }

    @PostMapping("/deleteMember")
    public String deleteMember(HttpServletRequest request,HttpSession session){
        String id = session.getAttribute("id").toString();
        Member member = memberService.findById(id);

        if(member.getPw().equals(request.getParameter("pw"))){ //java에서 String은 항상 equals로 비교하자 ==는 객체의 값이아닌 객체의 주소값을 비교한다.
            memberService.deleteMemberFromRepo(id);
            session.invalidate();
            return "redirect:/";
        }
        else {
            return "redirect:/deleteMemberPWC";
        }
    }
}
