package springProj.safeRestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import springProj.safeRestaurant.service.LoginService;
import springProj.safeRestaurant.service.MemberService;


import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;


    @PostMapping("/login")
    public String login(loginForm form, HttpSession session){
        String id = form.getId();
        String pw = form.getPw();

        if(loginService.loginCheck(id,pw)==true){
            session.setAttribute("loginCheck","pass");
            session.setAttribute("id",id);
            return "redirect:/";
        }
        return "loginPage";

    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}
