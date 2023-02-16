package com.xwb.learn;

import java.io.IOException;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

@Component
public class AuthFilterRegistrationBean extends FilterRegistrationBean<Filter> {
    // Filter使用 https://www.liaoxuefeng.com/wiki/1252599548343744/1282389221310497
    // Spring Boot会调用getFilter()，把返回的Filter注册到Servlet容器中
    @Override
    public Filter getFilter() {
        // Filter排序
        setOrder(10);
        return new AuthFilter();
    }

    class AuthFilter implements Filter {

        @Override
        public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
                throws IOException, ServletException {
            // TODO Auto-generated method stub

        }
    }
}
