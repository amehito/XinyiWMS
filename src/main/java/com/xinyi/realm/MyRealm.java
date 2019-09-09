package com.xinyi.realm;

import java.awt.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.xinyi.bean.XinyiRoles;
import com.xinyi.bean.XinyiUser;
import com.xinyi.bean.XinyiUserExample;
import com.xinyi.bean.XinyiUserExample.Criteria;
import com.xinyi.bean.XinyiUserRoleExample;
import com.xinyi.bean.XinyiUserRoleKey;
import com.xinyi.dao.XinyiRolesMapper;
import com.xinyi.dao.XinyiUserMapper;
import com.xinyi.dao.XinyiUserRoleMapper;
import com.xinyi.utils.MybatisOfSpringUtil;


public class MyRealm extends AuthorizingRealm {
	HashMap<String, String> userMap ;
	SqlSession sqlSession;
	XinyiUserMapper xinyiUserMapper;
	XinyiUserExample example;
	
	CredentialsMatcher matcher ;
	private XinyiUserRoleExample xinyiUserRoleExample;
	private com.xinyi.bean.XinyiUserRoleExample.Criteria createCriteria2;
	private java.util.List<XinyiUserRoleKey> selectByExample;
	private java.util.List<XinyiUser> xinyiUserList;
	
	{
		userMap = new HashMap<String, String>();
		userMap.put("Mark", "e10adc3949ba59abbe56e057f20f883e");
	}
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		
		
		// TODO Auto-generated method stub
		String userName = (String)arg0.getPrimaryPrincipal();
		Set<String> roles = getRolesByUserName(userName); 
		Set<String> permissions = getPermissionsByUserName(userName); 
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permissions);
		info.setRoles(roles);
		
		return info ;
	}

	private Set<String> getPermissionsByUserName(String userName) {
		// TODO Auto-generated method stub
		Set<String> sets = new HashSet<String>();
		sets.add("admin:delete");
		sets.add("admin:add");
		
		return sets;
	}
	public void setcredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		matcher =  credentialsMatcher;
	}
	private Set<String> getRolesByUserName(String userName) {
		// TODO Auto-generated method stub
		Set<String> sets = new HashSet<String>();
//		sets.add("user");
//		sets.add("admin");
		sqlSession = MybatisOfSpringUtil.getSessionFactory().openSession();
		xinyiUserMapper = sqlSession.getMapper(XinyiUserMapper.class);
		example = new XinyiUserExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUserUsernameEqualTo(userName);
	//	System.out.println("username="+userName);
		xinyiUserList = xinyiUserMapper.selectByExample(example);
		if(xinyiUserList.isEmpty())
			return sets;
//		System.out.println("xinyiUserListIs "+xinyiUserList.get(0).getUserUsername());
		XinyiUser xinyiUser = xinyiUserList.get(0);
		int userId = xinyiUser.getUserId();
		XinyiUserRoleMapper xinyiUserRoleMapper = sqlSession.getMapper(XinyiUserRoleMapper.class);
		xinyiUserRoleExample = new XinyiUserRoleExample();
		createCriteria2 = xinyiUserRoleExample.createCriteria();
		createCriteria2.andUserIdEqualTo(userId);
		selectByExample = xinyiUserRoleMapper.selectByExample(xinyiUserRoleExample);
		Iterator<XinyiUserRoleKey> iter = selectByExample.iterator();
		while(iter.hasNext()) {
			XinyiUserRoleKey key = iter.next();
			
			String item=sqlSession.getMapper(XinyiRolesMapper.class).selectByPrimaryKey(key.getRoleId()).getRoleName();
			System.out.println("item"+item);
			sets.add(item);
		}
		sqlSession.close();
		return sets;
		
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		// TODO Auto-generated method stub
		String userName = (String) arg0.getPrincipal();
		String password = getPasswordByUserName(userName);
		System.out.println("userName="+userName+"  password="+password);
		if(password==null)
			return null;
		Md5Hash md5Hash = new Md5Hash(password);
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userName,password,"MyRealm");
		
		return info;
		
	}

	private String getPasswordByUserName(String userName) {
		
		// TODO Auto-generated method stub
		sqlSession = MybatisOfSpringUtil.getSessionFactory().openSession();
		xinyiUserMapper = sqlSession.getMapper(XinyiUserMapper.class);
		example = new XinyiUserExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUserUsernameEqualTo(userName);
		XinyiUser xinyiUser = xinyiUserMapper.selectByExample(example).get(0);
		
		sqlSession.close();
		
		return xinyiUser.getUserPassword();
	}

}
