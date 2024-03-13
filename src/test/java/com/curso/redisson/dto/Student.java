package com.curso.redisson.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public record Student(
        String name,
        int age,
        String city,
        List<Integer> marks
) {
}
