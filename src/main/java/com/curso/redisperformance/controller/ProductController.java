package com.curso.redisperformance.controller;

import com.curso.redisperformance.entity.Product;
import com.curso.redisperformance.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("{id}")
    public Mono<Product> getProduct(@PathVariable int id) {
        return this.productService.getProduct(id);
    }

    @PutMapping("{id}")
    public Mono<Product> getProduct(@PathVariable int id, @RequestBody Mono<Product> body) {
        return this.productService.updateProduct(id, body);
    }

}
