package springProj.safeRestaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import springProj.safeRestaurant.domain.Restaurant;
import springProj.safeRestaurant.service.MemberService;
import springProj.safeRestaurant.service.MyPicksService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

@Controller
public class MypicksController {
    @Autowired MyPicksService myPicksService;
    @Autowired MemberService memberService;
    @PostMapping("/saveMypicks")
    public String saveMyPicks(HttpServletRequest request, HttpSession session){
        String[] restrNums = request.getParameterValues("restrNum");

        for(int i=0;i<restrNums.length;i++){
            myPicksService.addPick(session.getAttribute("id").toString(),Long.parseLong(restrNums[i]));
        }

        return "redirect:/";
    }

    @GetMapping("/showMyPicks")
    public String showMyPicks(HttpSession session, Model model) throws Exception {
        Set<Restaurant> myList = myPicksService.showMyPicks(session.getAttribute("id").toString());
        model.addAttribute("myList",myList);
        
        String name = memberService.findNameById(session.getAttribute("id").toString());
        model.addAttribute("name",name);
        return "/restaurant/myPicksList";
    }
}
