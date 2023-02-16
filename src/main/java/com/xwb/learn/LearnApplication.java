package com.xwb.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.web.reactive.function.client.WebClient;

import io.r2dbc.spi.ConnectionFactory;
import reactor.core.publisher.Mono;

// SpringBootApplication=@Configuration+@EnableAutoConfiguration+@ComponentScan
@SpringBootApplication
@EnableR2dbcRepositories
public class LearnApplication {

	@Bean
	public WebClient webClient() {
		return WebClient.create("http://localhost:8080");
	}

	@Bean
	ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);
		initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

		return initializer;
	}

	public static void main(String[] args) {
		// 响应式编程
		// Flux代表具有零个、一个或者多个（可能是无限个）数据项的管道，
		// 而Mono是一种特殊的反应式类型，针对数据项不超过一个的场景进行了优化
		Mono.just("Cating")
				.map(n -> n.toUpperCase())
				.map(up -> "------------Hello," + up + "!-------------------\n") // 这里实际上有3个Mono,just创建了一个,map分别创建一个
				.subscribe(System.out::println);

		SpringApplication.run(LearnApplication.class, args);
	}
}
