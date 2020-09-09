package springProj.safeRestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import springProj.safeRestaurant.domain.Member;
import springProj.safeRestaurant.service.MemberService;

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


}
