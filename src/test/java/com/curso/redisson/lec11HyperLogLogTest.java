package com.curso.redisson;

import org.junit.jupiter.api.Test;
import org.redisson.api.RHyperLogLogReactive;
import org.redisson.client.codec.LongCodec;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class lec11HyperLogLogTest extends BaseTest{

    @Test
    public void count() {
        RHyperLogLogReactive<Long> counter = this.client.getHyperLogLog("user:visits", LongCodec.INSTANCE);

        var longList = LongStream.rangeClosed(1,25000)
                        .boxed()
                        .collect(Collectors.toList());

        var longList2 = LongStream.rangeClosed(1,50_000)
                .boxed()
                .collect(Collectors.toList());

        var longList3 = LongStream.rangeClosed(50_001,100_000)
                .boxed()
                .collect(Collectors.toList());

        var longList4 = LongStream.rangeClosed(1,75_000)
                .boxed()
                .collect(Collectors.toList());

        var longList5 = LongStream.rangeClosed(50_001,100_000)
                .boxed()
                .collect(Collectors.toList());

        var mono = Flux.just(longList, longList2, longList3, longList4, longList5)
                        .flatMap(counter::addAll)
                        .then();

        StepVerifier.create(mono)
                .verifyComplete();

        counter.count()
                .doOnNext(System.out::println)
                .subscribe();
    }
}
