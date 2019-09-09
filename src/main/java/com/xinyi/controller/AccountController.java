package com.xinyi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.LogoutAware;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyi.bean.RegisterInfo;
import com.xinyi.bean.XinyiUser;
import com.xinyi.bean.XinyiUserExample;
import com.xinyi.bean.XinyiUserRoleKey;
import com.xinyi.bean.XinyiActionExample.Criteria;
import com.xinyi.common.Response;
import com.xinyi.common.ResponseUtil;
import com.xinyi.dao.XinyiUserMapper;
import com.xinyi.dao.XinyiUserRoleMapper;
import com.xinyi.exception.UserAccountServiceException;
import com.xinyi.service.accountService;
import com.xinyi.utils.MybatisOfSpringUtil;



@Controller
@RequestMapping("/account")
public class AccountController {
	
	ResponseUtil  responseUtil = new ResponseUtil();;
	
	@RequestMapping("/logout")
	public
    @ResponseBody
    Map<String, Object> logout() {
        // 初始化 Response
        Response response = responseUtil.newResponseInstance();

        Subject currentSubject = SecurityUtils.getSubject();
        if (currentSubject != null && currentSubject.isAuthenticated()) {
            // 执行账户注销操作
            currentSubject.logout();
            response.setResponseResult(Response.RESPONSE_RESULT_SUCCESS);
        } else {
            response.setResponseResult(Response.RESPONSE_RESULT_ERROR);
            response.setResponseMsg("did not login");
        }

        return response.generateResponse();
    }
	@RequestMapping(value = "passwordModify", method = RequestMethod.POST)
    
     @ResponseBody
    public Map<String, Object> passwordModify(@RequestBody Map<String, Object>passwordInfo,
                                       HttpServletRequest request) {
		System.out.println(passwordInfo);
        //初始化 Response
		
		
		Response responseContent = responseUtil.newResponseInstance();

        String errorMsg = null;
        String result = Response.RESPONSE_RESULT_ERROR;

        // 获取用户 ID
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("UserId");

     //   try {
            // 更改密码
            try {
				accountService.passwordModify(userID, passwordInfo);
				result = Response.RESPONSE_RESULT_SUCCESS;
            } catch (UserAccountServiceException e) {
				// TODO Auto-generated catch block
				errorMsg = e.getExceptionDesc();
			}

            
    //    } catch (UserAccountServiceException e) {
     //       errorMsg = e.getExceptionDesc();
     //   }
        // 设置 Response
        responseContent.setResponseResult(result);
        responseContent.setResponseMsg(errorMsg);
        return responseContent.generateResponse();
    }
	
	@RequestMapping("/register")
	public Map<String, Object> register(@RequestBody RegisterInfo info) {
		String newUsername = info.getNewUsername();
		String newPassword = info.getNewPassword();
		System.out.println(newPassword + "  username = "+newUsername);
		
		SqlSession sqlSession = MybatisOfSpringUtil.getSessionFactory().openSession();
		
		//先确认是否有该用户名
		
		 
		XinyiUserRoleKey record = new XinyiUserRoleKey();
		XinyiUser newUser = new XinyiUser();
		newUser.setUserUsername(newUsername);
		newUser.setUserPassword(newPassword);
		sqlSession.getMapper(XinyiUserMapper.class).insert(newUser);
		record.setUserId(newUser.getUserId());
		record.setRoleId(1);
		sqlSession.getMapper(XinyiUserRoleMapper.class).insert(record);
		sqlSession.commit();
		sqlSession.close();
		
		//response
		Response responseContent = responseUtil.newResponseInstance();
		String result = Response.RESPONSE_RESULT_SUCCESS;
		
		responseContent.setResponseResult(result);
        responseContent.setResponseMsg("success");
        return responseContent.generateResponse();
		
	}
	
 
}
