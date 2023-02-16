package com.xwb.learn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;

import com.xwb.learn.domain.DepartmentRepository;

@DataR2dbcTest
public class ShopDataTest {

    @Autowired
    DepartmentRepository shopRepo;

    @Test
    public void addShop() {
        System.out.println("shop data test");
        this.shopRepo.count().subscribe(System.out::println);
    }
}
