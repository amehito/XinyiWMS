package com.xinyi.utils;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.xinyi.dao.XinyiSupplierInfoMapper;
@Configuration
@EnableScheduling
public class AccessDB {
	//access to database every certain time in case connection to be closed
		@Scheduled(fixedRate = 1000000)   
		public void accessDB() {
			SqlSession sqlSession = MybatisOfSpringUtil.getSessionFactory().openSession();
			sqlSession.getMapper(XinyiSupplierInfoMapper.class).selectAll();
			System.out.println("访问数据库以免连接断开");
		}
}
