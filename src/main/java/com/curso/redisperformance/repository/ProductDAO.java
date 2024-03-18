package com.curso.redisperformance.repository;

import com.curso.redisperformance.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDAO extends ReactiveCrudRepository<Product, Integer> {
}
