package com.curso.rediscurso;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.redisson.api.RAtomicLongReactive;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
class RedisCursoApplicationTests {

	@Autowired
	private ReactiveStringRedisTemplate template;

	@Autowired
	private RedissonReactiveClient client;

	@RepeatedTest(3)
	void springDataRedisTest() {
		ReactiveValueOperations<String, String> valueOperations = this.template.opsForValue();

		long before = System.currentTimeMillis();
		var mono = Flux.range(1, 500_000)
					.flatMap(i -> valueOperations.increment("user:1:visit"))
					.then();

		StepVerifier.create(mono)
				.verifyComplete();

		long after = System.currentTimeMillis();
		System.out.println(after - before + " ms");

	}

	@RepeatedTest(3)
	public void redissonTest() {
		RAtomicLongReactive atomicLong = this.client.getAtomicLong("user:2:visit");
		long before = System.currentTimeMillis();
		var mono = Flux.range(1, 500_000)
				.flatMap(i -> atomicLong.incrementAndGet())
				.then();

		StepVerifier.create(mono)
				.verifyComplete();

		long after = System.currentTimeMillis();
		System.out.println(after - before + " ms");
	}

}
