package springProj.safeRestaurant.service;

import org.springframework.transaction.annotation.Transactional;
import springProj.safeRestaurant.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springProj.safeRestaurant.repository.MemberRepository;

import java.util.Optional;

@Transactional //jpa쓸려면 항상 있어야함
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;

    }

    public String join(Member member){
        IsAlreadyBeen(member);
        memberRepository.save(member);
        return member.getId();
    }

    public String findNameById(String id){
        Member temp = memberRepository.findById(id).orElse(null);
        String name ="";
        if(temp != null){
            name = temp.getName();
        }
        else{
            name = "null";
        }

        return name;
    }

    private void IsAlreadyBeen(Member member) {
        memberRepository.findById(member.getId()).ifPresent(m->{
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        });
    }


}
