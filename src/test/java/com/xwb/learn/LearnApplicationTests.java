package com.xwb.learn;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

import com.xwb.learn.domain.Department;
import com.xwb.learn.domain.DepartmentRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

@SpringBootTest
class LearnApplicationTests {
	private static final Logger log = LoggerFactory.getLogger(LearnApplicationTests.class);

	@Autowired
	DepartmentRepository shop;

	@Autowired
	WebClient webclient;

	@Test
	void contextLoads() {
		Mono.just("Cating")
				.map(n -> n.toUpperCase())
				.map(up -> "------------[in,context]Hello," + up + "!-------------------\n")
				.subscribe(txt -> {
					LearnApplicationTests.log.info(txt);
					System.out.println(txt);
				});
	}

	@Test
	public void monoFlux() {
		Mono.just("Cating")
				.map(n -> n.toUpperCase())
				.map(up -> "\n------------[in func]Hello," + up + "!-------------------\n")
				.timeout(Duration.ofSeconds(1))
				.subscribe(txt -> {
					LearnApplicationTests.log.error(txt);
					System.out.println("system" + txt);
				});
	}

	@Test
	public void createAFlux_fromArray() {
		String[] fruits = new String[] {
				"Apple", "Orange", "Grape", "Banana", "Strawberry" };

		Flux<String> fruitFlux = Flux.fromArray(fruits);

		StepVerifier.create(fruitFlux)
				.expectNext("Apple")
				.expectNext("Orange")
				.expectNext("Grape")
				.expectNext("Banana")
				.expectNext("Strawberry")
				.verifyComplete();
	}

	@Test
	public void createAFlux_fromIterable() {
		List<String> fruitList = new ArrayList<>();
		fruitList.add("Apple");
		fruitList.add("Orange");
		fruitList.add("Grape");
		fruitList.add("Banana");
		fruitList.add("Strawberry");

		Flux<String> fruitFlux = Flux.fromIterable(fruitList);

		StepVerifier.create(fruitFlux)
				.expectNext("Apple")
				.expectNext("Orange")
				.expectNext("Grape")
				.expectNext("Banana")
				.expectNext("Strawberry")
				.verifyComplete();
	}

	@Test
	public void createAFlux_fromStream() {
		Stream<String> fruitStream = Stream.of("Apple", "Orange", "Grape", "Banana", "Strawberry");
		Flux<String> fruitFlux = Flux.fromStream(fruitStream);

		StepVerifier.create(fruitFlux)
				.expectNext("Apple")
				.expectNext("Orange")
				.expectNext("Grape")
				.expectNext("Banana")
				.expectNext("Strawberry")
				.verifyComplete();
	}

	// 使用range产生从start开始的count个数
	@Test
	public void createAFlux_range() {
		Flux<Integer> intervalFlux = Flux.range(1, 5);

		StepVerifier.create(intervalFlux)
				.expectNext(1)
				.expectNext(2)
				.expectNext(3)
				.expectNext(4)
				.expectNext(5)
				.verifyComplete();
	}

	@Test
	public void createAFlux_interval() {
		Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1)).take(5);

		StepVerifier.create(intervalFlux)
				.expectNext(0L)
				.expectNext(1L)
				.expectNext(2L)
				.expectNext(3L)
				.expectNext(4L)
				.verifyComplete();
	}

	// 两个flux merge成一个
	@Test
	public void mergeFluxes() {
		// 在两个Flux流上使用delayElements()方法来减慢它们的速度，使它们每500毫秒发布一个条目
		// 不保证flux间的先后顺序
		Flux<String> characterFlux = Flux
				.just("Garfield", "Kojak", "Barbossa")
				.delayElements(Duration.ofMillis(500));
		Flux<String> foodFlux = Flux
				.just("Lasagna", "Lollipops", "Apples")
				.delaySubscription(Duration.ofMillis(250)) // 在订阅后250毫秒才开始发布数据
				.delayElements(Duration.ofMillis(500));

		Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux);

		StepVerifier.create(mergedFlux)
				.expectNext("Garfield")
				.expectNext("Lasagna")
				.expectNext("Kojak")
				.expectNext("Lollipops")
				.expectNext("Barbossa")
				.expectNext("Apples")
				.verifyComplete();
	}

	@Test
	public void zipFluxes() {
		Flux<String> characterFlux = Flux
				.just("Garfield", "Kojak", "Barbossa");
		Flux<String> foodFlux = Flux
				.just("Lasagna", "Lollipops", "Apples");

		Flux<Tuple2<String, String>> zippedFlux = Flux.zip(characterFlux, foodFlux);

		StepVerifier.create(zippedFlux)
				.expectNextMatches(p -> "Garfield".equals(p.getT1()) &&
						"Lasagna".equals(p.getT2()))
				.expectNextMatches(p -> "Kojak".equals(p.getT1()) &&
						"Lollipops".equals(p.getT2()))
				.expectNextMatches(p -> "Barbossa".equals(p.getT1()) &&
						"Apples".equals(p.getT2()))
				.verifyComplete();
	}

	@Test
	public void zipFluxesToObject() {
		System.out.println("LearnApplicationTests.zipFluxesToObject()");

		Flux<String> characterFlux = Flux
				.just("Garfield", "Kojak", "Barbossa");
		Flux<String> foodFlux = Flux
				.just("Lasagna", "Lollipops", "Apples");

		Flux<String> zippedFlux = Flux.zip(characterFlux, foodFlux, (c, f) -> c + " eats " + f);

		StepVerifier.create(zippedFlux)
				.expectNext("Garfield eats Lasagna")
				.expectNext("Kojak eats Lollipops")
				.expectNext("Barbossa eats Apples")
				.verifyComplete();
	}

	@Test
	public void skipAFewSeconds() {
		Flux<String> countFlux = Flux.just(
				"one", "two", "skip a few", "ninety nine", "one hundred")
				.delayElements(Duration.ofSeconds(1))
				.skip(Duration.ofSeconds(4))// 跳过元素
				.take(2);// 仅发布前几个数据项

		StepVerifier.create(countFlux)
				.expectNext("ninety nine", "one hundred")
				.verifyComplete();
	}

	// 过滤,filter
	@Test
	public void filter() {
		System.out.println("-----------filter------------------------------\n");
		Flux<String> nationalParkFlux = Flux.just(
				"Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton")
				.filter(np -> !np.contains(" "));

		StepVerifier.create(nationalParkFlux)
				.expectNext("Yellowstone", "Yosemite", "Zion")
				.verifyComplete();
	}

	@Test
	public void distinct() {
		Flux<String> animalFlux = Flux.just(
				"dog", "cat", "bird", "dog", "bird", "anteater")
				.distinct();

		StepVerifier.create(animalFlux)
				.expectNext("dog", "cat", "bird", "anteater")
				.verifyComplete();
	}

	// flatMap()并不像map()操作那样简单地将一个对象转换到另一个对象，而是将对象转换为新的Mono或Flux。结果形成的Mono或Flux会扁平化为新的Flux。
	@Test
	public void flatMap() {
		Flux<Player> playerFlux = Flux
				.just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
				.flatMap(n -> Mono.just(n)
						.map(p -> {
							String[] split = p.split("\\s");
							return new Player(split[0], split[1]);
						})
						// 调用subscribeOn()方法声明每个订阅都应该在并行线程中进行，
						// 因此可以异步并行地执行多个String对象的转换操作
						.subscribeOn(Schedulers.parallel()));

		List<Player> playerList = Arrays.asList(
				new Player("Michael", "Jordan"),
				new Player("Scottie", "Pippen"),
				new Player("Steve", "Kerr"));

		StepVerifier.create(playerFlux)
				.expectNextMatches(p -> playerList.contains(p))
				.expectNextMatches(p -> playerList.contains(p))
				.expectNextMatches(p -> playerList.contains(p))
				.verifyComplete();
	}

	@Test
	public void buffer() {
		Flux<String> fruitFlux = Flux.just(
				"apple", "orange", "banana", "kiwi", "strawberry");

		Flux<List<String>> bufferedFlux = fruitFlux.buffer(3);

		StepVerifier
				.create(bufferedFlux)
				.expectNext(Arrays.asList("apple", "orange", "banana"))
				.expectNext(Arrays.asList("kiwi", "strawberry"))
				.verifyComplete();
	}

	// buffer加并发
	@Test
	public void bufferAndFlatMap() throws Exception {
		Flux.just(
				"apple", "orange", "banana", "kiwi", "strawberry")
				.buffer(3)// 5个String值的Flux缓冲到一个新的由List集合组成的Flux
				.flatMap(x -> Flux.fromIterable(x) // 为每个List缓冲区中的元素创建一个新的Flux，然后对其应用map()操作
						.map(y -> y.toUpperCase())
						.subscribeOn(Schedulers.parallel())
						.log())
				.subscribe(System.out::println);
	}

	//
	@Test
	public void collectMap() {
		Flux<String> animalFlux = Flux.just(
				"aardvark", "elephant", "koala", "eagle", "kangaroo");

		Mono<Map<Character, String>> animalMapMono = animalFlux.collectMap(a -> a.charAt(0));

		StepVerifier
				.create(animalMapMono)
				.expectNextMatches(map -> {
					return map.size() == 3 &&
							"aardvark".equals(map.get('a')) &&
							"eagle".equals(map.get('e')) &&
							"kangaroo".equals(map.get('k'));
				})
				.verifyComplete();
	}

	// ------------------逻辑操作----------------
	@Test
	public void all() {
		Flux<String> animalFlux = Flux.just(
				"aardvark", "elephant", "koala", "eagle", "kangaroo");

		Mono<Boolean> hasAMono = animalFlux.all(a -> a.contains("a"));
		StepVerifier.create(hasAMono)
				.expectNext(true)
				.verifyComplete();

		Mono<Boolean> hasKMono = animalFlux.all(a -> a.contains("k"));
		StepVerifier.create(hasKMono)
				.expectNext(false)
				.verifyComplete();
	}

	@Test
	public void any() {
		Flux<String> animalFlux = Flux.just(
				"aardvark", "elephant", "koala", "eagle", "kangaroo");

		Mono<Boolean> hasTMono = animalFlux.any(a -> a.contains("t"));

		StepVerifier.create(hasTMono)
				.expectNext(true)
				.verifyComplete();
		Mono<Boolean> hasZMono = animalFlux.any(a -> a.contains("z"));
		StepVerifier.create(hasZMono)
				.expectNext(false)
				.verifyComplete();
	}

	@Test
	void findFirst() {
		System.out.println("--------------------------test-----------------");

		this.shop.findAll().take(1).subscribe(shop -> {
			System.out.println(shop.toString());
			LearnApplicationTests.log.info("shop:%s", shop.toString());// !!! format 为什么不生效
		});
	}

	/// -----------------web client,响应式的http
	/// client,响应式请求----------------------------
	@Test
	public void baseClient() {
		int id = 1;
		Mono<Department> shop = this.webclient
				.get()
				.uri("/{id}", id)
				.retrieve()
				.bodyToMono(Department.class);
		System.out.println("subcribe");
		shop.timeout(Duration.ofSeconds(1))// 超时设置
				.subscribe(s -> {
					System.out.println("\n---------------------------"
							+ s.toString() + "-----------------------------------\n");
				}, error -> {// 错误处理
					System.out.println(error);
				});
	}

	@Test
	public void baseClientError() {
		int id = 1;
		Mono<Department> shop = this.webclient
				.get()
				.uri("/{id}", id)
				.retrieve()
				.onStatus(status -> status == HttpStatus.NOT_FOUND,
						response -> Mono.just(new UnknownError()))
				.bodyToMono(Department.class);
		System.out.println("subcribe");
		shop.timeout(Duration.ofSeconds(1))// 超时设置
				.subscribe(s -> {
					System.out.println("\n---------------------------"
							+ s.toString() + "-----------------------------------\n");
				}, error -> {// 错误处理
					System.out.println(error);
				});
	}

	public static void main(String[] args) {
	}
}

// equals 必需,上面的测试用例中用到了
class Player {
	private final String firstName;
	private final String lastName;

	public Player(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.firstName == null) ? 0 : this.firstName.hashCode());
		result = prime * result + ((this.lastName == null) ? 0 : this.lastName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (this.firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!this.firstName.equals(other.firstName))
			return false;
		if (this.lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!this.lastName.equals(other.lastName))
			return false;
		return true;
	}
}