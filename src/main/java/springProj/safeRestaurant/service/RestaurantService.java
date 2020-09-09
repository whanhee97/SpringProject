package springProj.safeRestaurant.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springProj.safeRestaurant.domain.Restaurant;
import springProj.safeRestaurant.repository.MemoryRestrRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private final MemoryRestrRepository memoryRestrRepository;
    @Autowired
    public RestaurantService(MemoryRestrRepository memoryRestrRepository) {
        this.memoryRestrRepository = memoryRestrRepository;
    }

    public List<Restaurant> RestrLisetFoundByAddress(String keyword){
        List<Restaurant> list = memoryRestrRepository.findAll();
        return list.stream().filter(r ->r.getJibeon().contains(keyword)).collect(Collectors.toList());
    }
    public List<Restaurant> RestrLisetFoundByName(String keyword){
        List<Restaurant> list = memoryRestrRepository.findAll();
        return list.stream().filter(r ->r.getName().contains(keyword)).collect(Collectors.toList());
    }

    public Restaurant get(Long restrNum){
        List<Restaurant> list = memoryRestrRepository.findAll();
        return list.stream().filter(r -> r.getSafetyNo().equals(restrNum)).findFirst().orElse(null);
    }
    public List<Restaurant> getList(){
        return memoryRestrRepository.findAll();
    }

}
