package springProj.safeRestaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springProj.safeRestaurant.domain.Member;
import springProj.safeRestaurant.repository.MemberRepository;


@Service
public class LoginService {
    @Autowired private MemberRepository memberRepository;

    public boolean loginCheck(String id, String pw){
        Member temp = memberRepository.findById(id).orElse(null);
        if(temp != null){
            if(temp.getPw().equals(pw)){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
}
