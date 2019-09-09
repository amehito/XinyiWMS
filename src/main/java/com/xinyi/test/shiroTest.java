package com.xinyi.test;

import javax.security.auth.Subject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;

import com.xinyi.realm.MyRealm;

public class shiroTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		MyRealm realm = new MyRealm();
		
		securityManager.setRealm(realm);
		SecurityUtils.setSecurityManager(securityManager);
		org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
		Md5Hash md5Hash = new Md5Hash("123456");
		
		UsernamePasswordToken token = new UsernamePasswordToken("admin",md5Hash.toString());
		subject.login(token);
		System.out.println(subject.isAuthenticated());
		System.out.println(subject.hasRole("user"));
		System.out.println(subject.hasRole("admin"));
		
	}
}
