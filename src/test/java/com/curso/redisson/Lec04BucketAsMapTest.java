package com.curso.redisson;

import org.junit.jupiter.api.Test;
import org.redisson.client.codec.StringCodec;
import reactor.test.StepVerifier;

public class Lec04BucketAsMapTest extends BaseTest{

    @Test
    public void bucketsAsMap() {
        //user:1:name
        //user:2:name
        //user:3:name

        var mono = this.client.getBuckets(StringCodec.INSTANCE)
                .get("user:1:name", "user:2:name", "user:3:name")
                .doOnNext(System.out::println)
                .then();

        StepVerifier.create(mono)
                .verifyComplete();
    }
}
