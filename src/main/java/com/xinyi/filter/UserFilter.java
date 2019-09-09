package com.xinyi.filter;

import javax.security.auth.Subject;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;

public class UserFilter extends AccessControlFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest arg0, ServletResponse arg1, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		org.apache.shiro.subject.Subject subject = getSubject(arg0, arg1);
		String[] roles =  (String[]) arg2;
		if(roles==null || roles.length == 0) {
			return true;
		}
		for(String role:roles) {
			if(subject.hasRole(role))
				return true;
		}
		return false;
		
	}
	

	@Override
	protected boolean onAccessDenied(ServletRequest arg0, ServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
