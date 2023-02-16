package com.xwb.learn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.xwb.learn.domain.Department;
import com.xwb.learn.domain.DepartmentCurdRepository;
import com.xwb.learn.domain.DepartmentRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
@RestController
@CrossOrigin(origins = "*")
public class ShopController {

    @Autowired
    DepartmentRepository shoprepo;

    // @Autowired
    // DepartmentCurdRepository shop;

    @GetMapping("/hello")
    public String hello() {
        return "learn spring boot";
    }

    @GetMapping(value = "/{id}")
    public Mono<Department> findDepartmentById(@PathVariable("id") int id) {
        System.out.println("id=" + id);

        return this.shoprepo.findById((long) id);
    }

    @GetMapping(value = "shop/{dept}")
    public Flux<Department> findDepartmentByDept(@PathVariable("dept") String dept) {

        return this.shoprepo.findByDeptStartingWith(dept);
    }

    // 使用响应式构建api
    // @GetMapping(value = "shop/{id}")
    // public Mono<Department> findShopById(@PathVariable("id") int id) {
    // System.out.println("id=" + id);
    // return this.shop.findById((long) id);
    // }
}
