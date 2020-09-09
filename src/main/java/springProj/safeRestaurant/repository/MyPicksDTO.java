package springProj.safeRestaurant.repository;

import javax.persistence.*;

@Entity
@Table(name="mypicks")
public class MyPicksDTO {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="userid")
    private String userId;
    @Column(name="restr_num")
    private Long restrNum;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getRestrNum() {
        return restrNum;
    }

    public void setRestrNum(Long restrNum) {
        this.restrNum = restrNum;
    }
}
