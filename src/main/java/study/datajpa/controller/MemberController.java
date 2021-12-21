package study.datajpa.controller;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberRepository memberRepository;
	
	@GetMapping("/members/{id}")
	public String findMember(@PathVariable("id") Long id) {
		Member member = memberRepository.findById(id).get();
		
		return member.getUsername();
	}
	
	// 아래처럼 사용해도 위와 같이 동작한다.
	// 단 영속성 컨텍스트 내에서 관리가 되지 않기 때문에
	// 단순 조회용으로만 사용하는 것을 추천한다.
	// WHY? 예외 상황을 해결하기 위해 JPA의 깊숙한 곳까지 파고 들어가야할 수도 있기 때문.
	@GetMapping("/members2/{id}")
	public String findMember2(@PathVariable("id") Member member) {
//		Member member = memberRepository.findById(id).get();
		return member.getUsername();
	}
	
	@PostConstruct
	public void init() {
		memberRepository.save(new Member("userA"));
	}

}
