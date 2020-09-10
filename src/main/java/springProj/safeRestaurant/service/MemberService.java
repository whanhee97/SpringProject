package springProj.safeRestaurant.service;

import org.springframework.transaction.annotation.Transactional;
import springProj.safeRestaurant.controller.infoForm;
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

    public Member findById(String id){
        Member temp = memberRepository.findById(id).orElse(null);
        return temp;
    }
    
    public void updateMemberInfo(String id, infoForm nMember){
        Optional<Member> updateMember = memberRepository.findById(id); // 아이디로 기존 멤버 갖고와서
        updateMember.ifPresent(n->{ //있는지 검사하고 있으면 새로 변경될 데이터 세팅하면 jpa가 알아서 update쿼리 날림
            n.setName(nMember.getName());
            n.setAge(nMember.getAge());
            n.setGender(nMember.getGender());
            n.setAddress(nMember.getAddress());
        });
        
    }
    
    private void IsAlreadyBeen(Member member) {
        memberRepository.findById(member.getId()).ifPresent(m->{
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        });
    }


}
