package com.xinyi.service;

import org.springframework.stereotype.Service;

import com.xinyi.bean.XinyiUser;

@Service
public interface RestService {
	String selectByPrimaryKey(int id);
}
