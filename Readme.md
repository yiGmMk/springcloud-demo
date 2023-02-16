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

## 数据库

### Postgressql

配置

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
