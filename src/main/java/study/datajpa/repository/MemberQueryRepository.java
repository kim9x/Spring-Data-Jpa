 package study.datajpa.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {
	
	private final EntityManager em;
	
	// 꼭 사용자 정의 리포지토리가 필요한 것은 아니다.
	// 이처럼 임의의 리포지토리를 만들고
	// 빈으로 등록한 후에 주입받아서 사용해도 된다.
	List<Member> findAllMembers() {
		return em.createQuery("select m from Member m")
			.getResultList();
	}

}
