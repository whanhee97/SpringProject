package springProj.safeRestaurant.repository;

import java.util.List;

public interface MyPicksDAO {
    void insert(MyPicksDTO dto);
    List<MyPicksDTO> findByUserId(String userId);
    //void delete(Long restrNum);
    //void deleteAll(String userId);
}
