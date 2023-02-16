package com.xwb.learn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xwb.learn.domain.Person;
import com.xwb.learn.domain.PersonRepository;

@Configuration
@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register/{name}")
    public String register(@PathVariable("name") String name) {
        System.out.println("name=" + name);
        try {
            this.personRepo.save(new Person((long) 55, name, this.passwordEncoder.encode(name)));
        } catch (Exception e) {
            System.out.println("注册失败" + e);
        }
        return "redirect:/login";
    }
}
