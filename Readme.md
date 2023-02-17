# 项目

## 配置

### @SpringBootApplication

@SpringBootApplication=@Configuration+@EnableAutoConfiguration+@ComponentScan

#### @Configuration

Tags the class as a source of bean definitions for the application context.

#### @EnableAutoConfiguration

Tells Spring Boot to start adding beans based on classpath settings,
other beans, and various property settings. For example, if spring-webmvc
is on the classpath, this annotation flags the application as a web application and
activates key behaviors, such as setting up a DispatcherServlet.

#### @ComponentScan

Tells Spring to look for other components, configurations, and services
in the com/example package, letting it find the controllers.

### Bean要实现getter/setter方法

### profiles

使用@Profile生命,Bean仅在特定环境才创建

```java
@Bean
@Profile("dev") // 仅在dev下激活
public CommandLineRunner dataLoader(IngredientRepository repo,
      UserRepository userRepo, PasswordEncoder encoder) {

}

@Profile("!prod") // 非prod环境激活
```

## 数据库

### 数据库配置

```yml
spring:
  datasource:
    # 初始化sql
    schema:
    - order-schema.sql
    - ingredient-schema.sql
    - taco-schema.sql
    - user-schema.sql
    data:
    - ingredients.sql
    # 配置参数
    url: jdbc:mysql://localhost/tacocloud
    username: tacouser
    password: tacopassword
    driver-class-name: com.mysql.jdbc.Driver
```

### Postgressql

#### R2DBC配置

```yml
spring:
  main:
    web-application-type: reactive
  r2dbc:
    #两个都可以
    #url: r2dbc:postgresql:tcp://localhost:5432/learn
    url: r2dbc:postgresql://localhost:5432/learn
    username: postgres
    password: db-wrz2z
```

#### Repository

ReactiveCrudRepository 与 R2dbcRepository

```java
@NoRepositoryBean
public interface R2dbcRepository<T, ID> extends ReactiveCrudRepository<T, ID>, ReactiveSortingRepository<T, ID>, ReactiveQueryByExampleExecutor<T> {}
```

#### sql

```java
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
```

### 参考

- <https://www.cnblogs.com/binecy/p/15004375.html>

## OAuth2

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>
```

添加后普通接口都无法正常访问,需要实现OAuth2在请求中添加访问凭证

参考:

- <https://blog.csdn.net/u012702547/article/details/105699777>
- <https://blog.csdn.net/weixin_45982841/article/details/114378146>

## 网络请求

### 测试

报错"Connection prematurely closed BEFORE response"

- <https://www.jianshu.com/p/ee180c78f999>

报错"Failed to resolve 'localhost' after 2 queries"
