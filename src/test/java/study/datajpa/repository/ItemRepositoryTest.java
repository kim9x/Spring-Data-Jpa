package study.datajpa.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import study.datajpa.entity.Item;

@SpringBootTest
class ItemRepositoryTest {
	
	@Autowired ItemRepository itemRepository;

	@Test
	public void save() {
		
//		Item item = new Item();
		
		// item의 id 값이 null 값이 들어간다.
		// persist 할 때 id 값 생성.!
		// 여기서 말하는 id 값이란 Entity 부분에서
		// @id annotation이 지정된 것을 말한다.
//		itemRepository.save(item);
	}

}
