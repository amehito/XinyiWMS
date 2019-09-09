package com.xinyi.test;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinyi.bean.XinyiBatchStock;
import com.xinyi.bean.XinyiMaterial;
import com.xinyi.dao.XinyiBatchStockMapper;
import com.xinyi.dao.XinyiMaterialMapper;
import com.xinyi.utils.MybatisOfSpringUtil;

public class importFromExcel {
	

	@Test
	public void test()  {
		File csv = new File("C:\\Users\\45981\\Desktop\\stock.csv");
		try {
			BufferedReader br = new BufferedReader(new FileReader(csv));
			
			String line = "";
			String everyLine = "";
			SqlSession sqlSession = MybatisOfSpringUtil.getSessionFactory().openSession();
			XinyiBatchStockMapper mapper= sqlSession.getMapper(XinyiBatchStockMapper.class);
			List<String> allString = new ArrayList<String>();
			String[] materialInfo = new String[25];
			String[] index ; 
			XinyiBatchStock stock = new XinyiBatchStock();
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			while ((line = br.readLine()) != null) // 读取到的内容给line变量
			{
				
				everyLine = line;
				
				allString.add(everyLine);
				index = everyLine.split(",");
				stock.setMaterialid(index[0]);
				stock.setName(index[1]);
				stock.setNumber(Integer.parseInt(index[2]));
				stock.setPrice(Double.parseDouble(index[3]));
				stock.setBatch("MonAug05201916:02:14");
				stock.setPlus("13.0");
				stock.setManufacturer("库存");
				stock.setSupplier("库存");
				mapper.insert(stock);
			}
			sqlSession.commit();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	
	
}
