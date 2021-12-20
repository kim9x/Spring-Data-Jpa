package study.datajpa.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import study.datajpa.entity.Member;
import study.datajpa.repository.dto.MemberDto;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
	
	List<Member> findHelloBy();

	List<Member> findTop3HelloBy();
	
	// @Query annotation을 지워줘도 작동을 한다?!
	// cuz 'Member.findByUsername'의 네임드 쿼리를 먼저 찾는다.
	// (Member에 위치한 findByUsername로 설정된 named query)
	// 그 이후 메서드 명으로 쿼리 생성하는 것을 수행한다.
//	@Query(name = "Member.findByUsername")
	List<Member> findByUsername(@Param("username") String username);
	
	@Query("select m from Member m where m.username = :username and m.age = :age")
	List<Member> findUser(@Param("username") String username, @Param("age") int age);
	
	@Query("select m.username from Member m")
	List<String> findUsernameList();
	
	@Query("select new study.datajpa.repository.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
	List<MemberDto> findMemberDto();
	
	// in절을 자동으로 만들어줌.!
	@Query("select m from Member m where m.username in :names")
	List<Member> findByNames(@Param("names") Collection<String> names);
	
	
	List<Member> findListByUsername(String username); // 컬렉션
	Member findMemberByUsername(String username); // 단건
	Optional<Member> findOptionalByUsername(String username); // 단건 Optional
}
