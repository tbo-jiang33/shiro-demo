package com.jtb.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@MapperScan(basePackages = {"com.jtb.shiro.mapper"})
public class ShiroDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShiroDemoApplication.class, args);
	}

}
