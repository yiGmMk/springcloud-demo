package com.xwb.learn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.xwb.learn.domain.Department;
import com.xwb.learn.domain.DepartmentRepository;

import reactor.core.publisher.Mono;

@Configuration
@RestController
public class ShopController {
    // @Autowired
    // DepartmentService shop;
    @Autowired
    DepartmentRepository shoprepo;

    @GetMapping("/hello")
    public String hello() {
        return "learn spring boot";
    }

    @GetMapping(value = "/{id}")
    public Mono<Department> findDepartmentById(@PathVariable("id") int id) {
        System.out.println("id=" + id);

        return this.shoprepo.findById((long) id);
    }

    // 使用响应式构建api

}
