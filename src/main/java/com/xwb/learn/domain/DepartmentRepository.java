package com.xwb.learn.domain;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import reactor.core.publisher.Flux;

// 简单的查询语句
public interface DepartmentRepository extends R2dbcRepository<Department, Long> {

    @Query("select * from department c where c.dept=:name ")
    Flux<Department> findByName(String name);

}
