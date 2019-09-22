package com.xinyi.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinyi.bean.XinyiManufactures;
import com.xinyi.bean.XinyiSupplierInfo;
import com.xinyi.bean.XinyiSupplierInfoExample;
import com.xinyi.bean.XinyiSupplierInfoExample.Criteria;
import com.xinyi.dao.XinyiManufacturesMapper;
import com.xinyi.dao.XinyiMaterialMapper;
import com.xinyi.dao.XinyiSupplierInfoMapper;
import com.xinyi.dao.XinyiUserMapper;
import com.xinyi.utils.MybatisOfSpringUtil;

@Controller
@RequestMapping("/OtherInfo")
public class OthersController {
	
	
	
	private static ObjectMapper jsonCreater = new ObjectMapper() ;
	SqlSession sqlSession = MybatisOfSpringUtil.getSessionFactory().openSession();
//	XinyiMaterialMapper Mapper = sqlSession.getMapper(XinyiMaterialMapper.class);

	@RequestMapping(value="/manufactures",method=RequestMethod.GET
			,produces="application/json;charset=utf-8")
	@ResponseBody
	
	public String getManufacturesList() throws JsonProcessingException {
		XinyiManufacturesMapper mapper = sqlSession.getMapper(XinyiManufacturesMapper.class);
		List<XinyiManufactures> list = mapper.selectAll();
		String result = jsonCreater.writeValueAsString(list);
		System.out.println(result);
		return result ;
		
	}
	
	@RequestMapping(value="/videos",produces="plain/text;charset=utf-8")
	public void video(HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print("<!DOCTYPE html>\r\n" + 
				"<html lang=\"en\">\r\n" + 
				"<head>\r\n" + 
				"    <meta charset=\"UTF-8\">\r\n" + 
				"    <title>ajaxCros</title>\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" );
		File file = new File("D:\\videos");
		File[] files = file.listFiles();
		int num = 0;
		for(File f:files) {
			if(f.getName().endsWith("mp4")) {
				String url = "../videos/"+f.getName();
				out.println("<a href='"+url+"'>"+f.getName()+"</a></br>");
				num++;
			}
		}
		System.out.println(num);
		out.print("</body></html>");

	}
	
	
	@RequestMapping(value="/supplier",method=RequestMethod.GET
			,produces="application/json;charset=utf-8")
	public @ResponseBody String getSupplierInfo() throws JsonProcessingException {
		XinyiSupplierInfoMapper mapper = sqlSession.getMapper(XinyiSupplierInfoMapper.class);
		List<XinyiSupplierInfo> list = mapper.selectAll();
		System.out.println(list.size());
		
		return jsonCreater.writeValueAsString(list);
	}
	
	@RequestMapping(value="/editSupplier",produces="application/json;charset=utf-8")
	public @ResponseBody String editSupplier(@RequestBody XinyiSupplierInfo info) throws JsonProcessingException {
		XinyiSupplierInfoMapper mapper = sqlSession.getMapper(XinyiSupplierInfoMapper.class);
		XinyiSupplierInfoExample example = new XinyiSupplierInfoExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andNameEqualTo(info.getName());
		createCriteria.andTypeEqualTo(info.getType());
		List<XinyiSupplierInfo> list = mapper.selectByExample(example);
		if(list.size() == 0)	{
			XinyiSupplierInfo  supplierInfo = new XinyiSupplierInfo();
			supplierInfo.setName(info.getName());
			supplierInfo.setType(info.getType());
			mapper.insert(supplierInfo);
		}else {
			mapper.deleteByPrimaryKey(list.get(0).getId());
		}
		sqlSession.commit();
		String result = jsonCreater.writeValueAsString(mapper.selectAll());
		return result;
	}
	
	@RequestMapping(value="/editManufacturer",produces="application/json;charset=utf-8")
	public @ResponseBody String editManufacturer(@RequestBody XinyiSupplierInfo info) throws JsonProcessingException {
		XinyiSupplierInfoMapper mapper = sqlSession.getMapper(XinyiSupplierInfoMapper.class);
		XinyiSupplierInfoExample example = new XinyiSupplierInfoExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andNameEqualTo(info.getName());
		createCriteria.andTypeEqualTo(info.getType());
		List<XinyiSupplierInfo> list = mapper.selectByExample(example);
		if(list.size() == 0)	{
			XinyiSupplierInfo  supplierInfo = new XinyiSupplierInfo();
			supplierInfo.setName(info.getName());
			supplierInfo.setType(info.getType());
			mapper.insert(supplierInfo);
		}else {
			mapper.deleteByPrimaryKey(list.get(0).getId());
		}
		sqlSession.commit();
		String result = jsonCreater.writeValueAsString(mapper.selectAll());
		return result;
	}
}
