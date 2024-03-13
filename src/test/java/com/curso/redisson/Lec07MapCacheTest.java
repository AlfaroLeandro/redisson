package com.curso.redisson;

import com.curso.redisson.dto.Student;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RMapReactive;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Lec07MapCacheTest extends BaseTest{

    @Test
    public void mapCacheTesst() {
        //Map<Integer, Student>
        var codec = new TypedJsonJacksonCodec(Integer.class, Student.class);
        RMapCacheReactive<Integer,Student> mapCache = this.client.getMapCache("users:cache", codec);

        Student student1 = new Student("leandro", 22, "lafe", List.of(1,2,3));
        Student student2 = new Student("a", 1, "b", List.of(4,5,6));

        var mono1 = mapCache.put(1, student1, 5, TimeUnit.SECONDS);
        var mono2 = mapCache.put(2, student2, 10, TimeUnit.SECONDS);

        StepVerifier.create(mono1.then(mono2).then())
                .verifyComplete();

        sleep(3000);

        mapCache.get(1).doOnNext(System.out::println).subscribe();
        mapCache.get(2).doOnNext(System.out::println).subscribe();

        sleep(3000);

        mapCache.get(1).doOnNext(System.out::println).subscribe();
        mapCache.get(2).doOnNext(System.out::println).subscribe();
    }
}
