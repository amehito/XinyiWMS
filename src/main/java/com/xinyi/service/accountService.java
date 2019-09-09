package com.xinyi.service;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.xinyi.bean.XinyiUser;
import com.xinyi.bean.XinyiUserExample;
import com.xinyi.bean.XinyiUserRoleKey;
import com.xinyi.bean.XinyiActionExample.Criteria;
import com.xinyi.dao.XinyiUserMapper;
import com.xinyi.dao.XinyiUserRoleMapper;
import com.xinyi.exception.UserAccountServiceException;
import com.xinyi.utils.MybatisOfSpringUtil;

public class accountService {

	public static void passwordModify(Integer userID, Map<String, Object> passwordInfo) throws UserAccountServiceException {
		// TODO Auto-generated method stub
		System.out.println(userID);
		System.out.println(passwordInfo.get("newPassword"));
		String newPassword = (String)passwordInfo.get("newPassword");
		String oldPassword = (String)passwordInfo.get("oldPassword");
		SqlSession sqlSession = MybatisOfSpringUtil.getSessionFactory().openSession();
		XinyiUser user = sqlSession.getMapper(XinyiUserMapper.class).selectByPrimaryKey(userID);
		if(!user.getUserPassword().equals(oldPassword) ||newPassword==null||oldPassword==null) {
			System.out.println("密码错误");
			throw new UserAccountServiceException(UserAccountServiceException.PASSWORD_ERROR);
		}
		if(user==null) {
			throw new UserAccountServiceException(UserAccountServiceException.PASSWORD_ERROR);
		}
		else {

			XinyiUser record = new XinyiUser();
			System.out.println("id = "+userID);
			record.setUserPassword(newPassword);
			XinyiUserExample example = new XinyiUserExample();
			com.xinyi.bean.XinyiUserExample.Criteria criteria = example.createCriteria();
			criteria.andUserIdEqualTo(userID);
			
			sqlSession.getMapper(XinyiUserMapper.class).updateByExampleSelective(record, example);
			sqlSession.commit();
			sqlSession.close();
		}
	}

}
