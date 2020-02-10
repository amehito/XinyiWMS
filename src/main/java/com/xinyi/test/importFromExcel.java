/*package com.xinyi.test;

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
import com.xinyi.bean.XinyiBatchStockExample;
import com.xinyi.bean.XinyiBatchStockExample.Criteria;
import com.xinyi.bean.XinyiMaterial;
import com.xinyi.dao.XinyiBatchStockMapper;
import com.xinyi.dao.XinyiMaterialMapper;
import com.xinyi.utils.MybatisOfSpringUtil;

public class importFromExcel {
	
	
	public void material() {
		File csv = new File("C:\\Users\\45981\\Documents\\WeChat Files\\ywc959\\FileStorage\\File\\2019-12\\material.csv");
		try {
			BufferedReader br = new BufferedReader(new FileReader(csv));
			
			String line = "";
			String everyLine = "";
			SqlSession sqlSession = MybatisOfSpringUtil.getSessionFactory().openSession();
			//XinyiBatchStockMapper mapper= sqlSession.getMapper(XinyiBatchStockMapper.class);
			XinyiMaterialMapper mapper = sqlSession.getMapper(XinyiMaterialMapper.class);
			List<String> allString = new ArrayList<String>();
			String[] materialInfo = new String[25];
			String[] index ; 
			XinyiMaterial stock = new XinyiMaterial();
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			Date now = new Date();
			while ((line = br.readLine()) != null) // 读取到的内容给line变量
			{
				
				everyLine = line;
				allString.add(everyLine);
				index = everyLine.split(",");
				stock.setMaterialId(index[0]);
				stock.setViceId(index[1]);
				stock.setMaterialName(index[2]);
				stock.setMaterialSpec(index[3]);
				stock.setWarehousePosition(index[4]);
				stock.setPlus(index[5]);
				stock.setMaterialType(index[6]);
				stock.setMaterialUnit(index[7]);
				stock.setStartTime(now);
				try {
					stock.setMaterialPrice(Float.parseFloat(index[8]));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					stock.setMaterialPrice((float)0);
				}

				
				try {
					stock.setStockNumber(Integer.parseInt(index[9]));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					stock.setStockNumber(0);
				}

				
				try {
					stock.setStockSafe(Integer.parseInt(index[10]));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					stock.setStockSafe(0);
				}
				
				stock.setCreateManager("韩华新");
				mapper.insert(stock);
			}
			sqlSession.commit();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void test()  {
		File csv = new File("C:\\Users\\45981\\Documents\\WeChat Files\\ywc959\\FileStorage\\File\\2019-12\\import.csv");
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
				
				try {
					everyLine = line;
					allString.add(everyLine);
					index = everyLine.split(",");
					stock.setMaterialid(index[2]);
					stock.setName(index[1]);
					stock.setNumber(Integer.parseInt(index[4]));
					stock.setPrice((double)sqlSession.getMapper(XinyiMaterialMapper.class).selectByPrimaryKey(index[2]).getMaterialPrice());
					stock.setBatch("J191215001");
					stock.setPlus("13.0");
					stock.setManufacturer("库存");
					stock.setSupplier("库存");
					mapper.insert(stock);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					continue;
				}
			}
			sqlSession.commit();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	@Test
	public void getNumbers() {
		SqlSession sqlSession = MybatisOfSpringUtil.getSessionFactory().openSession();
		XinyiBatchStockMapper mapper= sqlSession.getMapper(XinyiBatchStockMapper.class);
		XinyiMaterialMapper mapper2 = sqlSession.getMapper(XinyiMaterialMapper.class);
		List<XinyiMaterial> list = mapper2.selectAll();
		System.out.println(list.size());
		for(XinyiMaterial m : list) {
			XinyiBatchStockExample example = new XinyiBatchStockExample();
			Criteria createCriteria = example.createCriteria();
			createCriteria.andMaterialidEqualTo(m.getMaterialId());
			XinyiBatchStock stock;
			try {
				stock = mapper.selectByExample(example).get(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				continue;
			}
			
			m.setStockNumber(stock.getNumber());;
			mapper2.updateByPrimaryKeySelective(m);
		}
		sqlSession.commit();
	}
	
	
	
}*/