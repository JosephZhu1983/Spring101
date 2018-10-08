package me.josephzhu.spring101data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Spring101DataApplicationTests {

	private static Random random = new Random();

	@Autowired
	InvestOrderRepository investOrderRepository;
	@Autowired
	ProjectRepository projectRepository;
	private static boolean initialized = false;

	List<InvestOrder> investOrders;

	@Before
	public void init(){
		if (!initialized) {
			projectRepository.deleteAll();
			IntStream.rangeClosed(1, 10).mapToObj(i -> {
				Project project = new Project();
				project.name = "标的" + i;
				project.totalAmount = new BigDecimal("10000");
				project.remainAmount = new BigDecimal("10000");
				project.status = random.nextInt(2);
				return project;
			}).forEach(projectRepository::save);
			initialized = true;
		}

		investOrderRepository.deleteAll();
		investOrders = IntStream.rangeClosed(1,100).mapToObj(i-> {
			InvestOrder investOrder = new InvestOrder();
			investOrder.userId = random.nextInt(1000);
			investOrder.userName = "用户" + investOrder.userId;
			investOrder.projectId = random.nextInt(10)+1;
			investOrder.projectName = "标的" + investOrder.projectId;
			investOrder.status = random.nextInt(3);
			investOrder.amount = new BigDecimal(String.valueOf(random.nextInt(10000)));
			return investOrder;
		}).collect(Collectors.toList());
		investOrders.forEach(investOrderRepository::save);
	}

	@Test
	public void test1() {
		Assert.assertEquals(
				investOrderRepository.findTop3ByStatusIs(1).map(o->o.id).collect(Collectors.toList()),
				investOrders.stream().filter(i->i.status==1).limit(3).map(o->o.id).collect(Collectors.toList()));
	}

	@Test
	public void test2() {
		System.out.println(investOrderRepository.findAll());
	}
}
