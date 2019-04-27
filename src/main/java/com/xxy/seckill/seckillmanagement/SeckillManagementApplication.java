package com.xxy.seckill.seckillmanagement;

		import org.springframework.boot.SpringApplication;
		import org.springframework.boot.autoconfigure.SpringBootApplication;
		import org.springframework.web.bind.annotation.RequestMapping;
		import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SeckillManagementApplication {

	public static void main(String[] args) {
		System.out.println("项目启动！");
		SpringApplication.run(SeckillManagementApplication.class, args);
	}

	@RequestMapping("/")
	public String home() {
		return "Hello World!";
	}

}
