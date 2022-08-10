package com.mall.file;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mall.file.**.dao")
public class MallFileApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallFileApplication.class, args);
	}

}
