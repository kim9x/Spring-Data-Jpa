package study.datajpa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import study.datajpa.entity.Team;

@Slf4j
@RestController
public class HelloController {

	@GetMapping("/hello")
	public String hello() {
		log.info("name");
		return "hello";
	}
	

}
