package com.bill.ChatroomAdvanced;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class ChatroomAdvancedApplication implements ApplicationRunner{

	public static void main(String[] args) {
		SpringApplication.run(ChatroomAdvancedApplication.class, args);
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("app running!");
		log.error("app is Error");
		log.warn("app is Warn");
		log.info("app is Info");
		log.debug("app is Debug");
	}

}
