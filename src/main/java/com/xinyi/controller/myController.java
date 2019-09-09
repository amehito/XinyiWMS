package com.xinyi.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.security.auth.Subject;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Response;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.mysql.cj.Session;
import com.xinyi.bean.XinyiUser;
import com.xinyi.bean.XinyiUserExample;
import com.xinyi.bean.XinyiUserExample.Criteria;
import com.xinyi.dao.XinyiUserMapper;
import com.xinyi.test.User;
import com.xinyi.utils.MatrixToImageWriter;
import com.xinyi.utils.MybatisOfSpringUtil;


@Controller
public class myController {
	
	@RequestMapping("/upload")
	public void saveUploadFiles(DefaultMultipartHttpServletRequest request  ) {
		
		
	}
	
	@RequestMapping("/test/qr-img")
	@ResponseBody
	public void getQRCode(String code_url, HttpServletResponse response) {   
        encodeQrcode("123", response);
   }
	
	
	 private void encodeQrcode(String content, HttpServletResponse response) {    
	        if (StringUtils.isEmpty(content))        return;    
	        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();    
	        Map hints = new HashMap();    
	        //设置字符集编码类型  
	        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); 
	        //设置二维码四周的白色边框 ,默认是4,默认为4的时候白色边框实在是太粗了   
	        hints.put(EncodeHintType.MARGIN, 0);
	        BitMatrix bitMatrix = null;    
	        try {        
	                bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 190, 190, hints);      
	                BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);   
	                //输出二维码图片流      
	                try {          
	                      ImageIO.write(image, "png", response.getOutputStream());     
	                     } catch (IOException e) {       
	                           // TODO Auto-generated catch block      
	                            e.printStackTrace();    
	                           }  
	                     } catch (WriterException e1) {     
	                           // TODO Auto-generated catch block       
	                           e1.printStackTrace();   
	                     }
	  }


	@RequestMapping(value="/subLogin",method=RequestMethod.POST
			,produces="application/json;charset=utf-8")
	
	public String subLogin(User user,HttpSession httpSession) {
		//Md5Hash md5Hash = new Md5Hash(user.getPassword());
		org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
		
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		httpSession.setAttribute("UserName", user.getUsername());
		SqlSession sqlSession = MybatisOfSpringUtil.getSessionFactory().openSession();
		sqlSession = MybatisOfSpringUtil.getSessionFactory().openSession();
		XinyiUserMapper xinyiUserMapper = sqlSession.getMapper(XinyiUserMapper.class);
		XinyiUserExample example = new XinyiUserExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUserUsernameEqualTo(user.getUsername());
		XinyiUser xinyiUser = xinyiUserMapper.selectByExample(example).get(0);
		Integer UserId = xinyiUser.getUserId();
		httpSession.setAttribute("UserId", UserId);
		
		sqlSession.close();
		
		
		return "mainPage";
		
	}
	@RequestMapping("/test")
	public void test(HttpServletResponse response) throws IOException {
		response.sendRedirect("login-page.html");
	}
	@RequestMapping("/mainPage")
	public String home() {
		org.apache.shiro.subject.Subject currentSubject = SecurityUtils.getSubject();
        if (!currentSubject.isAuthenticated())
            return "login";
        else {
        	System.out.println("重定向了");
            return "mainPage";
            
        }
	}
}
