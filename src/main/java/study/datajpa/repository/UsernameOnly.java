 package study.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {
	
	// open projection?
	// entity를 다 가져와서 @Value 안의 값들을 계산(?) 연산(?) 해준다.
//	@Value("#{target.username + ' ' + target.age}")
	String getUsername();

}
