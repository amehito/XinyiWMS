package com.xinyi.realm;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xinyi.realm.MyRealm;

@Configuration
public class ShiroConfiguration {
	@Bean
	public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}


	/**
	 * ShiroFilterFactoryBean 处理拦截资源文件问题。
	 * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在
	 * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
	 *
     Filter Chain定义说明
     1、一个URL可以配置多个Filter，使用逗号分隔
     2、当设置多个过滤器时，全部验证通过，才视为通过
     3、部分过滤器可指定参数，如perms，roles
	 *
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
		System.out.println("ShiroConfiguration.shirFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();

		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		
		//未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/nopermissions.html");
		shiroFilterFactoryBean.setLoginUrl("login.html");
		//拦截器.
		//Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
		//自定义拦截器
		Map<String, Filter> filtersMap = new LinkedHashMap<>();

        filtersMap.put("roleOrFilter",new RoleOrFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);
        Map<String ,String> filterChainDefinitionMap = new LinkedHashMap<>();
		
		//配置映射关系
		filterChainDefinitionMap.put("/images/*", "anon");
		filterChainDefinitionMap.put("/videos/*", "anon");
		filterChainDefinitionMap.put("/OtherInfo/*", "anon");

		filterChainDefinitionMap.put("/upload.html", "anon");
		filterChainDefinitionMap.put("/upload", "anon");
		filterChainDefinitionMap.put("/getXinyiUser", "anon");
		filterChainDefinitionMap.put("/material/*", "anon");
		filterChainDefinitionMap.put("/otherInfo/*", "roleOrFilter[admin,user]");
		filterChainDefinitionMap.put("/test/*", "anon");
		filterChainDefinitionMap.put("/picking.html", "anon");
		filterChainDefinitionMap.put("/login.html", "anon");
		filterChainDefinitionMap.put("/subLogin", "anon");
		filterChainDefinitionMap.put("/mainPage", "anon");
		
		filterChainDefinitionMap.put("/updatePermissionss.html", "anon");
		filterChainDefinitionMap.put("/blogin.html", "anon");
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/index", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/img/**", "anon");
		filterChainDefinitionMap.put("/config/**", "anon");
		filterChainDefinitionMap.put("/main.html", "anon");
		filterChainDefinitionMap.put("/imageUpload1", "roleOrFilter[admin,chief]");		
		filterChainDefinitionMap.put("/main_machineinfo.html", "roleOrFilter[admin,chief]");
		filterChainDefinitionMap.put("/updateuser.html", "roles[admin]"); 
		filterChainDefinitionMap.put("/permissions.html", "roles[admin]"); 
		filterChainDefinitionMap.put("/special-fault.html", "roleOrFilter[admin,chief]");
		filterChainDefinitionMap.put("/fault-review.html","roleOrFilter[admin,chief,monitor]");
		filterChainDefinitionMap.put("/board.html","roleOrFilter[admin,chief,monitor,worker,productManager]");		
		filterChainDefinitionMap.put("/*", "authc");
		filterChainDefinitionMap.put("/doLogout", "logout");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	    @Bean(name = "sessionManager")
	    public DefaultWebSessionManager sessionManager() {
	    	DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
	    	// 设置session过期时间3600s
	    	sessionManager.setGlobalSessionTimeout(-3600000L);
	    	return sessionManager;
	    }

	@Bean
	public SecurityManager securityManager(){
		DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
		//设置realm.
		securityManager.setRealm(getDatabaseRealm());
        securityManager.setSessionManager(sessionManager());
		return securityManager;
	}

	@Bean
	public MyRealm getDatabaseRealm(){
		MyRealm myShiroRealm = new MyRealm();
		// myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return myShiroRealm;
	}


	/**
	 *  开启shiro aop注解支持.
	 *  使用代理方式;所以需要开启代码支持;
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
}