package com.curso.rediscurso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class RedisCursoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisCursoApplication.class, args);
	}

}
