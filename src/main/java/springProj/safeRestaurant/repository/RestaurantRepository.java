package springProj.safeRestaurant.repository;

import springProj.safeRestaurant.domain.Restaurant;

import java.util.List;

public interface RestaurantRepository {
    public void save(Restaurant restaurant);
    public List<Restaurant> findAll();
}
