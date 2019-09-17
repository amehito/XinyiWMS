package com.xinyi.start;


import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;


@SpringBootApplication(scanBasePackages={"com.xinyi.*"})

@MapperScan({"com.xinyi.dao"})
@ComponentScan("com.xinyi.*")
public class XinyiWmsApplication  {

	
	public static void main(String[] args) {
		SpringApplication.run(XinyiWmsApplication.class, args);
	}
	
	
	
}
