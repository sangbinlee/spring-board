package com.smartscore.board;

import java.time.LocalDateTime;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@EnableJpaAuditing
@SpringBootApplication
@Slf4j
public class Vue3BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(Vue3BoardApplication.class, args);
	}








    @PostConstruct
    public void init() {
        // timezone 설정
//        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));



        LocalDateTime now = LocalDateTime.now();
        log.info("현재시간={}", now);
        log.info("현재시간={}", now);
        log.info("현재시간={}", now);
        log.info("현재시간={}", now);
        log.info("현재시간={}", now);
//        현재시간=2024-04-30T02:43:52.417264100
//        현재시간=2024-04-30T11:43:23.218953900

//        JWT expired 69407821 milliseconds ago
//        at 2024-04-29T07:37:53.000Z.
//        Current time: 2024-04-30T02:54:40.821Z.
//        Allowed clock skew: 0 milliseconds.
    }



}
