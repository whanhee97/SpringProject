package springProj.safeRestaurant.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springProj.safeRestaurant.domain.Restaurant;
import springProj.safeRestaurant.service.RestaurantService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;

    @GetMapping("restrList")
    public String RestaurantList(Model model, HttpSession session) throws Exception {

        List<Restaurant> list = restaurantService.getList();
        model.addAttribute("restrList",list);

        System.out.println();

        if(session.getAttribute("id") != null)
            return "/restaurant/restrListL";
        else
            return "/restaurant/restrList";

    }
    @GetMapping("findByAddress")
    public String findByAddress(@RequestParam("keyword") String keyword,Model model,HttpSession session) throws Exception {
        List<Restaurant> list = restaurantService.RestrLisetFoundByAddress(keyword);
        model.addAttribute("restrList",list);


        if(session.getAttribute("id") != null)
            return "/restaurant/restrListL";
        else
            return "/restaurant/restrList";
    }

    @GetMapping("findByName")
    public String findByName(@RequestParam("keyword") String keyword,Model model,HttpSession session) throws Exception {
        List<Restaurant> list = restaurantService.RestrLisetFoundByName(keyword);
        model.addAttribute("restrList",list);


        if(session.getAttribute("id") != null)
            return "/restaurant/restrListL";
        else
            return "/restaurant/restrList";

    }

    @PostMapping("/showMap")
    public String showMap(HttpServletRequest request,Model model){
        String[] restrNumList = request.getParameterValues("restaurant");
        int index = Integer.parseInt(request.getParameter("submit"));

        //System.out.println(index);
        Restaurant restaurant = restaurantService.get(Long.parseLong(restrNumList[index]));
        model.addAttribute("restaurant",restaurant);
        //return "redirect:/";
        return "/restaurant/restrInfo";
    }


}
