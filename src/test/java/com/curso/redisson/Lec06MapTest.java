package com.curso.redisson;

import com.curso.redisson.dto.Student;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMapReactive;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;

public class Lec06MapTest extends BaseTest{

    @Test
    public void mapTest() {
        RMapReactive<String, String> map = this.client.getMap("user:1", StringCodec.INSTANCE);

        var name = map.put("name", "leandro");
        var age = map.put("age", "22");
        var city = map.put("city", "lafe");

        StepVerifier.create(name.concatWith(age).concatWith(city).then())
                .verifyComplete();
    }

    @Test
    public void mapTest2() {
        RMapReactive<String, String> map = this.client.getMap("user:2", StringCodec.INSTANCE);
        var mono = map.putAll(Map.of("name", "a",
                    "age", "b",
                    "city", "c"));

        StepVerifier.create(mono)
                .verifyComplete();
    }
    @Test
    public void mapTest3() {
        //Map<Integer, Student>
        var codec = new TypedJsonJacksonCodec(Integer.class, Student.class);
        RMapReactive<Integer, Student> map = this.client.getMap("users", codec);

        Student student1 = new Student("leandro", 22, "lafe", List.of(1,2,3));
        Student student2 = new Student("a", 1, "b", List.of(4,5,6));

        var mono1 = map.put(1, student1);
        var mono2 = map.put(2, student2);

        StepVerifier.create(mono1.concatWith(mono2).then())
                .verifyComplete();

    }
}
