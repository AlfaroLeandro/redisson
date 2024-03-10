package com.curso.redisson;

import com.curso.redisson.config.RedissonConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {
    protected RedissonReactiveClient client;

    private RedissonConfig redissonConfig = new RedissonConfig();

    @BeforeAll
    public void setClient() {
        this.client = redissonConfig.getReactiveClient();
    }

    @AfterAll
    public void shutdown() {
        this.client.shutdown();
    }

    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
