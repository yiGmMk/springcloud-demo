package com.xwb.learn.domain;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// 简单的查询语句 参考: https://www.cnblogs.com/binecy/p/15004375.html
// 规则: 
// findByName           ->findBy<fieldName>
// findByIdGreaterThan  ->findBy<fieldName>GreaterThan
public interface DepartmentRepository extends R2dbcRepository<Department, Long> {

    // ---------- 按照规则,自动生成sql----------------------

    Flux<Department> findByDept(String dept);

    // 只要我们按规则定义方法名，Spring就会为我们生成SQL。
    // 查找大于给定id的数据
    Flux<Department> findByIdGreaterThan(Long startId);

    // 查询名称以给定字符串开头的数据
    Flux<Department> findByDeptStartingWith(String dept);

    // 分页
    Flux<Department> findByIdGreaterThanEqual(Long startId, Pageable pageable);

    // -------------------手写sql-----------------------------
    @Query("select * from department where id in (:ids)")
    Flux<Department> findByIds(List<Long> ids);

    @Modifying
    @Query("update department set dept = :dept where id = :id")
    Mono<Department> updateDept(@Param("id") long id, @Param("dept") String dept);
}
