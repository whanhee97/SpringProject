package springProj.safeRestaurant.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springProj.safeRestaurant.domain.Restaurant;

import java.util.List;

@SpringBootTest
public class RestaurantServiceTest {
    @Autowired RestaurantService restaurantService;

    @Test
    public void 테스트() throws Exception {
        List<Restaurant> restrList = restaurantService.getRestrList();
        System.out.println(restrList.get(1).getJibeon());
    }
}
