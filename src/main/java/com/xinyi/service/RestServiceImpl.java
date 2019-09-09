package com.xinyi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyi.bean.XinyiUser;
import com.xinyi.dao.XinyiUserMapper;
@Service
public class RestServiceImpl implements RestService{
	@Autowired
	private XinyiUserMapper xinyiUserMapper;
	@Override
	public String selectByPrimaryKey(int id) {
		// TODO Auto-generated method stub
		return "abc";
	}

}
