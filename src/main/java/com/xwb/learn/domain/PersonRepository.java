package com.xwb.learn.domain;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import reactor.core.publisher.Mono;

public interface PersonRepository extends R2dbcRepository<Person, Long> {
    Mono<Person> findByName(String name);
}
