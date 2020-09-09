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
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Set;

@Controller
public class MypicksController {
    @Autowired MyPicksService myPicksService;
    @Autowired MemberService memberService;
    @PostMapping("/saveMypicks")
    public String saveMyPicks(HttpServletRequest request, HttpSession session){
        String[] restrNums = request.getParameterValues("restrNum"); // 이렇게 하면 스트링으로 된 체크박스 벨류들을 가져옴 따라서 long으로 변환 필요

        for(int i=0;i<restrNums.length;i++){
            myPicksService.addPick(session.getAttribute("id").toString(),Long.parseLong(restrNums[i])); // 두번째 인자 long으로 변환해줌
        }

        return "redirect:/restrList";
    }

    @GetMapping("/showMyPicks")
    public String showMyPicks(HttpSession session, Model model) throws Exception {
        Set<Restaurant> myList = myPicksService.showMyPicks(session.getAttribute("id").toString());
        model.addAttribute("myList",myList);
        
        String name = memberService.findNameById(session.getAttribute("id").toString());
        model.addAttribute("name",name);
        return "/restaurant/myPicksList";
    }

    @PostMapping("/deletePicks")
    public String deletePicks(HttpServletRequest request, HttpSession session){
        String[] restrNums = request.getParameterValues("restrNum");

        for(int i=0;i<restrNums.length;i++){
            myPicksService.deletePick(session.getAttribute("id").toString(),Long.parseLong(restrNums[i]));
        }

        return "redirect:/showMyPicks";
    }
    @GetMapping("/deleteAllPicks")
    public String deleteAllPicks(HttpSession session){
        myPicksService.deleteAllPicks(session.getAttribute("id").toString());
        return "redirect:/showMyPicks";
    }
}
