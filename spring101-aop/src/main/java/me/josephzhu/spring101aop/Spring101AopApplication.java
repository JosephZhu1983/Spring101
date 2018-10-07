package me.josephzhu.spring101aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.Duration;

@SpringBootApplication
//@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
public class Spring101AopApplication implements CommandLineRunner {

	@Autowired
	private MyService myService;

	public static void main(String[] args) {
		SpringApplication.run(Spring101AopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			myService.insertData(true);
			myService.insertData(false);
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		System.out.println(myService.getData(new MyBean(0L, "zhuye",35, new BigDecimal("1000")),
				5,
				Duration.ofSeconds(1)));
	}
}
