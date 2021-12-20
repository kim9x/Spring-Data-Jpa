package study.datajpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import study.datajpa.entity.Member;
import study.datajpa.entity.Team;
import study.datajpa.repository.dto.MemberDto;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

	@Autowired MemberRepository memberRepository;
	@Autowired TeamRepository teamRepository;
	
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
	
	@Test
	public void basicCRUD() {
		Member member1 = new Member("member1");
		Member member2 = new Member("member2");
		memberRepository.save(member1);
		memberRepository.save(member2);
		
		// 단건 조회 검증
		Member findMember1 = memberRepository.findById(member1.getId()).get();
		Member findMember2 = memberRepository.findById(member2.getId()).get();
		assertThat(findMember1).isEqualTo(member1);
		assertThat(findMember2).isEqualTo(member2);
		
		// 리스트 조회 검증
		List<Member> all = memberRepository.findAll();
		assertThat(all.size()).isEqualTo(2);
		
		// 카운트 검증
		long count = memberRepository.count();
		assertThat(count).isEqualTo(2);
		
		// 삭제 검증
		memberRepository.delete(member1);
		memberRepository.delete(member2);
		long deletedCount = memberRepository.count();
		assertThat(deletedCount).isEqualTo(0);
	}
	
	@Test
	public void findByUsernameAndAgeGreaterThen() {
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("AAA", 20);
		
		memberRepository.save(m1);
		memberRepository.save(m2);
		
		List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);
		
		assertThat(result.get(0).getUsername()).isEqualTo("AAA");
		assertThat(result.get(0).getAge()).isEqualTo(20);
		assertThat(result.size()).isEqualTo(1);
	}
	
	@Test
	public void findrHelloBy() {
		List<Member> helloBy = memberRepository.findTop3HelloBy();
	}
	
	@Test
	public void testNamedQuery() {
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("BBB", 20);
		
		memberRepository.save(m1);
		memberRepository.save(m2);
		
		List<Member> result = memberRepository.findByUsername("AAA");
		Member findMember = result.get(0);
		
		assertThat(findMember).isEqualTo(m1);
	}
	
	@Test
	public void testQuery() {
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("BBB", 20);
		
		memberRepository.save(m1);
		memberRepository.save(m2);
		
		List<Member> result = memberRepository.findUser("AAA", 10);
		assertThat(result.get(0)).isEqualTo(m1);
		
	}
	
	@Test
	public void findUsernamreList() {
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("BBB", 20);
		
		memberRepository.save(m1);
		memberRepository.save(m2);
		
		List<String> usernameList = memberRepository.findUsernameList();
		for (String s : usernameList) {
			System.out.println("s = " + s);
		}
	}
	
	@Test
	public void findMemberDto() {
		Team team = new Team("teamA");
		teamRepository.save(team);
		
		Member m1 = new Member("AAA", 10);
		m1.setTeam(team);
		memberRepository.save(m1);
		
		List<MemberDto> memberDto = memberRepository.findMemberDto();
		for (MemberDto dto : memberDto) {
			System.out.println("dto = " + dto);
		}
	}
	
	@Test
	public void findByNames() {
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("BBB", 20);
		
		memberRepository.save(m1);
		memberRepository.save(m2);
		
		
		List<Member> result = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
		for (Member member : result) {
			System.out.println("member = " + member);
		}
	}
	
	@Test
	public void returnType() {
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("BBB", 20);
		
		memberRepository.save(m1);
		memberRepository.save(m2);
		
		
		// 만약 매치되는 값이 없을 때
		// empty Collection을 반환해준다.
		// null 값이 아닌 것을 보장해준다.
		List<Member> findListByUsername = memberRepository.findListByUsername("AAA");
		System.out.println("result = " + findListByUsername.size());
		
		// 단건인 경우 결과가 없으면
		// null 값을 반환해준다.
		// JPA에서는 null 값이 반환된다면 Exception을 발생시키지만
		// Spring DATA JPA에서는 null 값을 반환해주도록 처리되어있다.
		Member findMemberByUsername = memberRepository.findMemberByUsername("AAA");
		System.out.println("result = " + findMemberByUsername);
		
		Optional<Member> findOptionalByUsername = memberRepository.findOptionalByUsername("AAA");
	}

}
