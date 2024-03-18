package com.curso.redisperformance.service;

import com.curso.redisperformance.entity.Product;
import com.curso.redisperformance.repository.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductDAO productDAO;

    public Mono<Product> getProduct(int id){
        return this.productDAO.findById(id);
    }

    public Mono<Product> updateProduct(int productId, Mono<Product> productMono) {
        return this.productDAO.findById(productId)
                    .flatMap(p -> productMono.doOnNext(pr -> pr.setId(productId)))
                    .flatMap(this.productDAO::save);
    }
}
