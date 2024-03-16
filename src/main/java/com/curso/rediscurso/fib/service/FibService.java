package com.curso.rediscurso.fib.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class FibService {

    /**
     * guarda como math:fib en redis
     * usa solo index como key
     *
     * hay que tener una estrategia para evitar cachear
     */
    @Cacheable(value = "math:fib", key = "#index")
    public int getFib(int index, String name) {
        System.out.println("calculating fib for idx: " + index + " name: " + name);
        return fib(index);
    }

    //PUT / POST / PATCH / DELETE
    @CacheEvict(value = "math:fib", key = "#index")
    public void clearCache(int index) {
        System.out.println("clearing hash key");
    }


    //intentional O(2^N)
    private int fib(int idx) {
        if(idx<2)
            return idx;
        return fib(idx-1) + fib(idx-2);
    }
}
