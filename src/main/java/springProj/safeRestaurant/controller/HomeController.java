package springProj.safeRestaurant.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springProj.safeRestaurant.service.MemberService;

import javax.servlet.http.HttpSession;


@Controller
public class HomeController {
    @Autowired private MemberService memberService;

    @GetMapping("/")
    public String home(HttpSession session,Model model){

        if(session.getAttribute("loginCheck") == "pass") {
            String name = memberService.findNameById(session.getAttribute("id").toString());
            model.addAttribute("name",name);
            return "loggedMain";
        }

        return "main";
    }

    @GetMapping("/loginPage")
    public String login(){
        return "loginPage";
    }

    @GetMapping("/members/new")
    public String createMemberForm(){
        return "members/createMemberForms";
    }
}
