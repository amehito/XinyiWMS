package com.xinyi.start;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xinyi.dao.XinyiMaterialMapper;
import com.xinyi.service.MaterialDataService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class test {
	@Autowired
	XinyiMaterialMapper XinyiMaterialMapper;
	@Autowired
	MaterialDataService materialDataService;
	@Test
	public void test() {
		System.out.println("start:");
		System.out.println(XinyiMaterialMapper.selectAll().size());
	}
}
