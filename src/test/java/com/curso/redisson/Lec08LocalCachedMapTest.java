package com.curso.redisson;

import com.curso.redisson.config.RedissonConfig;
import com.curso.redisson.dto.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.options.LocalCachedMapOptions;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

public class Lec08LocalCachedMapTest extends BaseTest{

    private RLocalCachedMap<Integer, Student> studentsMap;

    @BeforeAll
    public void setupClient() {
        RedissonConfig config = new RedissonConfig();
        var redissonClient = config.getClient();

        LocalCachedMapOptions<Integer, Student> mapOptions = LocalCachedMapOptions
                                .<Integer, Student>name("key")
                                .syncStrategy(LocalCachedMapOptions.SyncStrategy.UPDATE)
                                .reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.NONE)
                                .codec(new TypedJsonJacksonCodec(Integer.class, Student.class));

        studentsMap = redissonClient.getLocalCachedMap(mapOptions);
    }

    @Test
    public void appServer1(){
        Student student1 = new Student("leandro", 22, "lafe", List.of(1,2,3));
        Student student2 = new Student("a", 1, "b", List.of(4,5,6));
        this.studentsMap.put(1, student1);
        this.studentsMap.put(2, student2);

        Flux.interval(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println(i + " ==> " + studentsMap.get(1)))
                .subscribe();

        sleep(600000);
    }

    @Test
    public void appServer2(){
        Student student1 = new Student("leandro-actualizado", 22, "lafe", List.of(1,2,3));
        this.studentsMap.put(1, student1);
    }
}
