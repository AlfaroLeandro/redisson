package com.curso.rediscurso.weather.controller;

import com.curso.rediscurso.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("weather")
public class WeatherController {

    @Autowired
    private WeatherService service;

    @GetMapping("{zip}")
    public Mono<Integer> getWeather(@PathVariable int zip) {
        System.out.println("updating weather");
        return Mono.fromSupplier(() -> this.service.getInfo(zip));
    }
}
