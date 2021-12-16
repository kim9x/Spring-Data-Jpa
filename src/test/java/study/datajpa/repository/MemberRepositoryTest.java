package study.datajpa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import study.datajpa.entity.Member;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

	@Autowired MemberRepository memberRepository;
	
	@Test
	public void testMember() {
		Member member = new Member("MemberA");
		Member savedMember = memberRepository.save(member);
		
//		Member member2 = memberRepository.findById(savedMember.getId()).orElse(null);
		
//		Optional<Member> byId = memberRepository.findById(savedMember.getId());
//		Member member1 = byId.get();
		
		Member findMember = memberRepository.findById(savedMember.getId()).get();
		
		assertThat(findMember.getId()).isEqualTo(member.getId());
		assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
		assertThat(findMember).isEqualTo(member);
	}

}
