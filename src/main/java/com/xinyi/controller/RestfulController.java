package com.xinyi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xinyi.bean.XinyiUser;
import com.xinyi.service.RestService;

@RestController
public class RestfulController {
	@Autowired
	private RestService restService;
	
	@GetMapping("/getXinyiUser")
	public String getXinyiUser(int id) {
		return restService.selectByPrimaryKey(id);
	}
}
