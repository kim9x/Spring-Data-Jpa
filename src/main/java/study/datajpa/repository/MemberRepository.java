package study.datajpa.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import study.datajpa.entity.Member;
import study.datajpa.repository.dto.MemberDto;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
	
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
	
	// 원장 쿼리와 카운트 쿼리를 분리할 수 있다.
	@Query(value = "select m from Member m left join m.team t"
			, countQuery = "select count(m) from Member m")
	Page<Member> findByAge(int age, Pageable pageable);
//	Slice<Member> findByAge(int age, Pageable pageable);
//	List<Member> findByAge(int age, Pageable pageable);
	 
	// @Modifying의 아래 옵션에서 영속성 컨텍스트의 flush와 clear가 가능하다. 
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Member m set m.age = m.age + 1 where m.age >= :age")
	int bulkAgePlus(@Param("age") int age);
	
	@Query("select m from Member m left join fetch m.team")
	List<Member> findMemberFetchJoin();
	
	// @EntityGraph로 fetch join을 쉽게할 수 있다.
	@Override
	@EntityGraph(attributePaths = {"team"})
	List<Member> findAll();
	
	// JPQL에 '@EntityGraph'를 추가해서 fetch join을 쉽게 할 수도 있다. 
	@EntityGraph(attributePaths = {"team"})
	@Query("select m from Member m")
	List<Member> findMemberEntityGraph();
	
	// find..by 사이의 '..' 구간엔
	// TOP3같은 예약어로 지정되너 있는 것들을 제외하고 아무 값이나 들어가도 된다.
	// 메소드 네임으로 지정하는 경우에도
	// '@EntityGraph'를 이용해서 fetch join을 쉽게할 수 있다.
//	@EntityGraph(attributePaths = {"team"})
	@EntityGraph("Member.all")
	List<Member> findEntityGraphByUsername(@Param("username") String username);
	
	@QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true")) 
	Member findReadOnlyByUsername(String username);
	
	// select for update
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<Member> findLockByUsername(String username);
}
