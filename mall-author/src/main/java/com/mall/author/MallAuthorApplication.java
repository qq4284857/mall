package com.mall.author;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mall.author.**.dao")
public class MallAuthorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallAuthorApplication.class, args);
	}

}
