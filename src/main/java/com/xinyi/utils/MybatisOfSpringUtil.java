package com.xinyi.utils;

import java.io.IOException;
import java.io.Reader;

import javax.annotation.Resource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisOfSpringUtil {
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;
	static String resource = "mybatis-config.xml";
	static {
		try {
			reader = Resources.getResourceAsReader(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static SqlSessionFactory getSessionFactory() {
		return sqlSessionFactory;
	}
}
