package com.curso.redisson;

import org.junit.jupiter.api.Test;
import org.redisson.client.codec.LongCodec;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Lec09ListQueueStackTest extends BaseTest{

    @Test
    public void listTest() {

        var list = this.client.getList("number-input", LongCodec.INSTANCE);
        var mono = Flux.range(1,10)
                        .map(Long::valueOf)
                        .flatMap(list::add)
                        .then();
        StepVerifier.create(mono)
                .verifyComplete();

        StepVerifier.create(list.size())
                .expectNext(10)
                .verifyComplete();
    }

    @Test
    public void queueTest() {

        var queue = this.client.getQueue("number-input", LongCodec.INSTANCE);
        var mono = queue.poll()
                    .repeat(3)
                    .doOnNext(System.out::println)
                    .then();

        StepVerifier.create(mono)
                .verifyComplete();

        StepVerifier.create(queue.size())
                .expectNext(6)
                .verifyComplete();

    }

}
