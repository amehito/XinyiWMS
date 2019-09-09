package com.xinyi.realm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.xinyi.dao")
public class MyBatisConfig {
}