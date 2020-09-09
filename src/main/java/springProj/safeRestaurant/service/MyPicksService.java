package springProj.safeRestaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springProj.safeRestaurant.domain.Restaurant;
import springProj.safeRestaurant.repository.MyPicksDAO;
import springProj.safeRestaurant.repository.MyPicksDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class MyPicksService {
    private final MyPicksDAO myPicksDAO;
    private final RestaurantService restaurantService;
    @Autowired
    public MyPicksService(MyPicksDAO myPicksDAO, RestaurantService restaurantService) {
        this.myPicksDAO = myPicksDAO;
        this.restaurantService = restaurantService;
    }


    public void addPick(String userId,Long restrNum){
        MyPicksDTO myPicksDTO = new MyPicksDTO();
        myPicksDTO.setUserId(userId);
        myPicksDTO.setRestrNum(restrNum);
        myPicksDAO.insert(myPicksDTO);
    }

    public Set<Restaurant> showMyPicks(String userId) throws Exception {
        List<MyPicksDTO>dtoList = myPicksDAO.findByUserId(userId);
        Set<Restaurant>result = new HashSet<>();
        for(MyPicksDTO d : dtoList){
            result.add(restaurantService.get(d.getRestrNum()));
        }
        return result;
    }

    public void deletePick(String userId,Long restrNum){
        myPicksDAO.delete(userId,restrNum);
    }

    public void deleteAllPicks(String userId){
        myPicksDAO.deleteAll(userId);
    }
}
