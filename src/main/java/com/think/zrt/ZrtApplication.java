package com.think.zrt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.think.zrt.dao.ProductMapper;

@SpringBootApplication
@ServletComponentScan
@MapperScan(basePackageClasses = { ProductMapper.class })
@PropertySource({ "classpath:config/sysconfig.properties" })
public class ZrtApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZrtApplication.class, args);
	}
}
