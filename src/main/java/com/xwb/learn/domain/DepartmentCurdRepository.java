package com.xwb.learn.domain;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//简单的查询语句
public interface DepartmentCurdRepository extends ReactiveCrudRepository<Department, Long> {

    @Query("select * from department c where c.dept=:name ")
    Flux<Department> findByName(String name);

    Mono<Department> findByDept(String dept);
}
