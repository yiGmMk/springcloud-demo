package com.xwb.learn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xwb.learn.domain.Department;
import com.xwb.learn.domain.DepartmentCurdRepository;
import com.xwb.learn.domain.DepartmentRepository;

import jakarta.validation.constraints.Min;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
@RestController
@CrossOrigin(origins = "*")
@ConfigurationProperties(prefix = "learn.pagination")
@Validated
@RequestMapping("/shop")
public class ShopController {

    @Min(value = 10, message = "分页数量最小为5")
    private int pagesize = 10;

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

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
