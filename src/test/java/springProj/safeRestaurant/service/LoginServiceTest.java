package springProj.safeRestaurant.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springProj.safeRestaurant.domain.Member;
import springProj.safeRestaurant.repository.MemberRepository;

@SpringBootTest
public class LoginServiceTest {
    @Autowired LoginService loginService;
    @Autowired MemberService memberService;

    @Test
    public void login(){
        String id = "whanhee97";
        String pw = "1234";
        Member member = new Member();
        member.setName("이환희");
        member.setId("whanhee97");
        member.setAddress("dksdid");
        member.setPw("1234");
        member.setAge(24);
        member.setGender("male");

        memberService.join(member);

        System.out.println(loginService.loginCheck(id,pw));

    }

}
