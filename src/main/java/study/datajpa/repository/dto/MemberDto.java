package study.datajpa.repository.dto;

import lombok.Data;
import study.datajpa.entity.Member;

@Data
public class MemberDto {
	
	private Long id;
	private String username;
	private String teamName;
	
	public MemberDto(Long id, String username, String teamName) {
		this.id = id;
		this.username = username;
		this.teamName = teamName;
	}
	
	// DTO를 쉽게 생성하여 return하는 방법
	// 아래 방식으로 사용하면 method reference 방식을 사용하여
	// 코드가 훨씬 짧아질 수 있다.
	public MemberDto(Member member) {
		this.id = member.getId();
		this.username = member.getUsername();
	}

}
