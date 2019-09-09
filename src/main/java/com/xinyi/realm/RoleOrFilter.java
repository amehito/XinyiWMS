package com.xinyi.realm;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class RoleOrFilter extends AuthorizationFilter{

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		// TODO Auto-generated method stub
		Subject subject = getSubject(request,response);
		String[] roles = (String[])mappedValue;
		if(roles == null || roles.length ==0){
            return true;
        }
		
		 for(String role:roles){

	            if(subject.hasRole(role)){

	                return true;

	            }

	        }
		
		
		return false;
	}

}
