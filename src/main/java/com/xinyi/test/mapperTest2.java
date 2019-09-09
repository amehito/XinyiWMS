package com.xinyi.test;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.xinyi.dao.XinyiUserMapper;

import ch.qos.logback.core.Context;

@Service
public class mapperTest2 {
	@Autowired
	public  XinyiUserMapper xinyiUserMapper;
	@Autowired
	private ApplicationContext context;

	@Test
	public void test() {
		xinyiUserMapper.selectByPrimaryKey(5);
	}
}
