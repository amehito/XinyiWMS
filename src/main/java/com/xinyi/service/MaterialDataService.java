package com.xinyi.service;


import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinyi.bean.XinyiActionExample.Criteria;
import com.xinyi.bean.XinyiBatchStock;
import com.xinyi.bean.XinyiBatchStockExample;
import com.xinyi.bean.XinyiImport;
import com.xinyi.bean.XinyiMaterial;
import com.xinyi.bean.XinyiMaterialExample;
import com.xinyi.bean.XinyiModifyhistory;
import com.xinyi.bean.XinyiPicking;
import com.xinyi.bean.XinyiPickingExample;
import com.xinyi.dao.XinyiBatchStockMapper;
import com.xinyi.dao.XinyiImageMapper;
import com.xinyi.dao.XinyiImportMapper;
import com.xinyi.dao.XinyiMaterialMapper;
import com.xinyi.dao.XinyiModifyhistoryMapper;
import com.xinyi.dao.XinyiPickingMapper;
import com.xinyi.test.Material;
import com.xinyi.test.notifyModel;
import com.xinyi.utils.MybatisOfSpringUtil;



@Service

public class MaterialDataService {
	@Autowired
	 XinyiImportMapper testMapper;
	@Autowired
	XinyiImageMapper imageMapper;
	public static ObjectMapper jsonCreater = new ObjectMapper();
	static SqlSession sqlSession = MybatisOfSpringUtil.getSessionFactory().openSession();
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	static XinyiMaterialMapper materialMapper = sqlSession.getMapper(XinyiMaterialMapper.class);

	public   String getMaterialInfo() throws JsonProcessingException {	
		ArrayList<XinyiMaterial> list = (ArrayList<XinyiMaterial>) materialMapper .selectAll();	
		jsonCreater.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		String result = jsonCreater.writeValueAsString(list);
	//	System.out.println("service"+result);
		return result; 
		
	}
	public   String getmodifyhistoryInfo() throws JsonProcessingException {
		// TODO Auto-generated method stub
		
		XinyiModifyhistoryMapper mapper = sqlSession.getMapper(XinyiModifyhistoryMapper.class);
		ArrayList<XinyiModifyhistory> list = (ArrayList<XinyiModifyhistory>) mapper.selectAll();	
		jsonCreater.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		String result = jsonCreater.writeValueAsString(list);
		return result;
	}
	public   String changematerialnumber(XinyiMaterial info) {
		System.out.println(info.getStockNumber());
		int num =info.getStockNumber() ;
		XinyiMaterialMapper mapper = sqlSession.getMapper(XinyiMaterialMapper.class);
		XinyiMaterial record = new XinyiMaterial();
		record.setStockNumber(num);
	//	mapper.updateByExampleSelective(record, example);
		// TODO Auto-generated method stub
		return null;
	}
	public   String addMaterial(XinyiMaterial info) {
		System.out.println(info.getMaterialId());
		System.out.println(info.getMaterialName());
		info.setStartTime(new Date());
		info.setCreateManager("测试数据");
		String result = "插入成功";
		// TODO Auto-generated method stub
		XinyiMaterialMapper mapper = sqlSession.getMapper(XinyiMaterialMapper.class);
		try {
			mapper.insert(info);
		}catch (Exception e) {
			// TODO: handle exception
			mapper.updateByPrimaryKeySelective(info);
			result  = "已经存在该信息,以将值修改为最新的";
			System.out.println(e.toString());
		}
		finally {
			sqlSession.commit();
		}
		
		return result;
	}
	public   void addChangeHistory(XinyiMaterial info) {
		// TODO Auto-generated method stub
		XinyiModifyhistoryMapper mapper = sqlSession.getMapper(XinyiModifyhistoryMapper.class);
		XinyiModifyhistory record = new XinyiModifyhistory();
		record.setMaterialid(info.getMaterialId());
		record.setMaterialname(info.getMaterialName());
		record.setMaterialnumber(info.getStockNumber());
		record.setMaterialunit(info.getMaterialUnit());
		record.setModifymanager(info.getCreateManager());
		record.setModifyname("存入");
		record.setModifytime(new Date());
		mapper.insert(record );
		sqlSession.commit();
		
	}
	public   void savePickRequest(notifyModel notify) throws ParseException, JsonProcessingException {
		// TODO Auto-generated method stub
		try {
			XinyiPickingMapper mapper = sqlSession.getMapper(XinyiPickingMapper.class);
			XinyiPicking record = new XinyiPicking();
			System.out.println("admin:"+notify.getAdmin());
			record.setBaoxiuId(notify.getBaoxiuId());
			record.setName(notify.getAdmin());
			record.setTime(new Date());
			System.out.println(notify.getTime()+"传入时间："+notify.getTime());
			String maString = jsonCreater.writeValueAsString(notify.getMaterials());
			record.setMaterials(maString);
			record.setPlus("未通过");
		//	record.setMaterials("测试");
			System.out.println("material:"+maString);

			mapper.insertSelective(record);
			sqlSession.commit();
			
			System.out.println("savePickRequest:");
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
	}
	public   ArrayList<XinyiPicking> getUncompletes() {
		// TODO Auto-generated method stub
		
		XinyiPickingMapper mapper = sqlSession.getMapper(XinyiPickingMapper.class);
		
		XinyiPickingExample example = new XinyiPickingExample();
		com.xinyi.bean.XinyiPickingExample.Criteria criteria = example.createCriteria();
		criteria.andPlusEqualTo("未通过");
		sqlSession.clearCache();
		
		ArrayList<XinyiPicking> list =(ArrayList<XinyiPicking>) mapper.selectByExample(example );
		return list;
	}
	private void InitData(int id, ArrayList<Material> materials) {
		// TODO Auto-generated method stub
		XinyiPickingMapper mapper = sqlSession.getMapper(XinyiPickingMapper.class);
		String data = mapper.selectByPrimaryKey(id).getMaterials();
		com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray.parseArray(data);
		for(int i=0;i<jsonArray.size();i++) {
			com.alibaba.fastjson.JSONObject object = jsonArray.getJSONObject(i);
		    for(Material m:materials) {
		    	if(m.getMaterialId().equals(object.get("materialId"))) {
		    		System.out.println("修改前 "+object.get("materialId")+" 的值：" + object.get("number"));
		    		object.put("number", m.getNumber());
		    	}
		    }
    		System.out.println("修改后 "+object.get("materialId")+" 的值：" + object.get("number"));
		}
		XinyiPicking record = mapper.selectByPrimaryKey(id);
		record.setMaterials(jsonArray.toJSONString());
		mapper.updateByPrimaryKeySelective(record);
		sqlSession.commit();
		
	}
	public   boolean passRequest(int id,String admin, ArrayList<Material> materials) {
		// TODO Auto-generated method stub
		XinyiPickingMapper mapper = sqlSession.getMapper(XinyiPickingMapper.class);
	    XinyiBatchStockMapper stockMapper = sqlSession.getMapper(XinyiBatchStockMapper.class);
	 
	    //先修改传进来的值
		InitData(id,materials);

	    //
	    
	    
		try {
			List<Material> list = new ArrayList<Material>();
			XinyiPicking record = new XinyiPicking();
			record.setId(id);
			record.setPlus(admin+"通过");
			//修改材料表中的数量
			XinyiMaterialMapper materialMapper = sqlSession.getMapper(XinyiMaterialMapper.class);
			XinyiMaterial material;
			String data = mapper.selectByPrimaryKey(id).getMaterials();
			com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray.parseArray(data);
		    
			for(int i=0;i<jsonArray.size();i++) {
				com.alibaba.fastjson.JSONObject object = jsonArray.getJSONObject(i);
			    String materialId =	(String) object.get("materialId");   
			    int num = object.getIntValue("number");
			    material = materialMapper.selectByPrimaryKey(materialId);
			    int stockNum = material.getStockNumber();
			    material.setStockNumber(stockNum - num);
			    double materialTablePrice = material.getMaterialPrice();
			    materialMapper.updateByPrimaryKey(material);
			    
				//更改batchStock表数量
				XinyiBatchStockExample stockExample = new XinyiBatchStockExample();
			    com.xinyi.bean.XinyiBatchStockExample.Criteria criteria = stockExample.createCriteria();
			    criteria.andMaterialidEqualTo((String) object.get("materialId"));
			    List<XinyiBatchStock> stockList = stockMapper.selectByExample(stockExample);
			    int _num = num;
			    double totalPrice = 0;
			    System.out.println(stockList.size());
			    for(XinyiBatchStock item:stockList) {
			    	
			    	if(item.getNumber()==0 && stockList.size()>=2) {
			    		stockMapper.deleteByPrimaryKey(item.getId());
			    		continue;
			    	}
			    	
			    	if(item.getNumber() > _num) {
					    
			    		if(item.getPrice()<=0.1) {
			    			totalPrice += _num * materialTablePrice;
			    			
			    		}
			    		else {
			    			totalPrice += _num * item.getPrice();
			    		}
					    Material materialJson = new Material();

			    		materialJson.setTaxRate(item.getPlus());
					    materialJson.setManufacturing(item.getManufacturer());
					    materialJson.setSupplier(item.getSupplier());
				    	System.out.println("hhhbatch:"+item.getBatch());
				    	materialJson.setPrice(item.getPrice());
					    materialJson.setMaterialSpec(material.getMaterialSpec());
					    materialJson.setWarehousePosition(material.getWarehousePosition());
					    materialJson.setSize(material.getMaterialType());
					    materialJson.setTotalPrice(_num*item.getPrice());
					    materialJson.setMaterialId(materialId);
					    materialJson.setNumber(_num);
					    materialJson.setMaterial((String) object.get("material"));
					    materialJson.setUnit((String) object.get("unit"));
						materialJson.setBatch(item.getBatch());
						list.add(materialJson);
			    		item.setNumber(item.getNumber()-_num);
			    		_num = 0;
			    		stockMapper.updateByPrimaryKeySelective(item);
						sqlSession.commit();

			    		break;
			    	}
			    	else { 	
					    Material materialJson = new Material();

			    		totalPrice += item.getNumber()*item.getPrice();
			    		_num -=item.getNumber();
			    		
			    		materialJson.setTaxRate(item.getPlus());
					    materialJson.setManufacturing(item.getManufacturer());
					    materialJson.setSupplier(item.getSupplier());
					    materialJson.setPrice(item.getPrice());
					    materialJson.setMaterialSpec(material.getMaterialSpec());
					    materialJson.setWarehousePosition(material.getWarehousePosition());
					    materialJson.setSize(material.getMaterialType());
					    materialJson.setTotalPrice(item.getNumber()*item.getPrice());
					    materialJson.setMaterialId(materialId);
					    materialJson.setNumber(item.getNumber());
					    materialJson.setMaterial((String) object.get("material"));
					    materialJson.setUnit((String) object.get("unit"));
					    materialJson.setBatch(item.getBatch());
					    list.add(materialJson);
					    item.setNumber(0);
					    
			    		stockMapper.updateByPrimaryKeySelective(item);
						sqlSession.commit();

			    	}
			    }
			    //将价格添加到json中
			    System.out.println("totalPrice"+totalPrice);
			    System.out.println("materialnum:"+num+"  _num:"+_num);
			   
			}
			String factJson = jsonCreater.writeValueAsString(list);
			record.setFactMaterials(factJson);
			mapper.updateByPrimaryKeySelective(record);
			sqlSession.commit();
		    
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			return false;
		}
		
		return true;
		
	}
	
	
	
	private   void updateBatchStock() {
		// TODO Auto-generated method stub
		
	}
	public   boolean declineRequest(int id, String admin) {
		// TODO Auto-generated method stub
		XinyiPickingMapper mapper = sqlSession.getMapper(XinyiPickingMapper.class);
		try {
			XinyiPicking record = new XinyiPicking();
			record.setId(id);
			record.setPlus(admin+"拒绝");
			mapper.updateByPrimaryKeySelective(record);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			return false;
		}
		
		return true;
	}
	public   String getAllRequestInfo() throws JsonProcessingException {
		// TODO Auto-generated method stub
		XinyiPickingMapper mapper = sqlSession.getMapper(XinyiPickingMapper.class);
		jsonCreater.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss"));
		String result = jsonCreater.writeValueAsString(mapper.selectAll());

		return result;
	}
	public   String saveList(List<XinyiImport> info,HttpSession session) {
		// TODO Auto-generated method stub
		XinyiImportMapper mapper = sqlSession.getMapper(XinyiImportMapper.class);
		jsonCreater.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss"));
		try {
			for(XinyiImport item : info) {
				mapper.insert(item);
			}
			sqlSession.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "添加失败，请稍后再试";
		}
		//更新材料数据
		XinyiMaterialExample example = new XinyiMaterialExample();
		try {
			for(XinyiImport item : info) {
				com.xinyi.bean.XinyiMaterialExample.Criteria criteria = example.createCriteria();
				criteria.andMaterialIdEqualTo(item.getMaterialId());
				XinyiMaterial material = materialMapper.selectByPrimaryKey(item.getMaterialId());
				
				if(material == null) {
					material = new XinyiMaterial();
					material.setMaterialId(item.getMaterialId());
					material.setViceId(item.getViceId());
					material.setMaterialName(item.getMaterialName());
					material.setMaterialSpec(item.getMaterialSpec());
					material.setWarehousePosition(item.getWarehousePosition());
					material.setPlus(item.getPlus());
					//type 和size对应
					material.setMaterialType(item.getSize());
					material.setMaterialUnit(item.getUnit());
					material.setMaterialPrice(item.getPrice().floatValue());
					material.setStockNumber(item.getImportNumber());
					material.setStockSafe(item.getImportNumber());
					material.setBatchManage(item.getBatchManage());
					material.setStartTime(new Date());
					if(session.getAttribute("UserName")==null) {
						material.setCreateManager("admin");
					}
					else{
						material.setCreateManager((String) session.getAttribute("UserName"));
					}
						
					materialMapper.insert(material);
					
				}
				else {
					material.setViceId(item.getViceId());
					material.setMaterialSpec(item.getMaterialSpec());
					material.setWarehousePosition(item.getWarehousePosition());
					material.setPlus(item.getPlus());
					//type 和size对应
					material.setMaterialType(item.getSize());
					material.setMaterialUnit(item.getUnit());
					material.setStockNumber(item.getImportNumber()+material.getStockNumber());
					material.setFinishTime(new Date());
					material.setChangeManager((String) session.getAttribute("UserName"));
					materialMapper.updateByPrimaryKeySelective(material);
				}
				
			}
			sqlSession.commit();
			//记录到库存表中
			addToStockTable(info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			return "添加成功";
		}
	}
	private   void addToStockTable(List<XinyiImport> info) {
		// TODO Auto-generated method stub
		XinyiBatchStock batchStock = new XinyiBatchStock();
		XinyiBatchStockMapper mapper = sqlSession.getMapper(XinyiBatchStockMapper.class);
		for(XinyiImport item:info) {
			batchStock.setBatch(item.getBatchManage());
			batchStock.setMaterialid(item.getMaterialId());
			batchStock.setName(item.getMaterialName());
			batchStock.setNumber(item.getImportNumber());
			batchStock.setPrice(item.getPriceIncludeTax());
			batchStock.setPlus(item.getTaxRate().toString());
			batchStock.setManufacturer(item.getManufacturing());
			batchStock.setSupplier(item.getSupplier());
			mapper.insert(batchStock);
		}
		sqlSession.commit();
	}
	public  String getAllImportInfo() throws JsonProcessingException {
		// TODO Auto-generated method stub
		System.out.println(testMapper == null);
		testMapper.selectAll();
		XinyiImportMapper mapper = sqlSession.getMapper(XinyiImportMapper.class);
		List<XinyiImport> list  = mapper.selectAll();
		jsonCreater.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		String result = jsonCreater.writeValueAsString(list);
		return result;
	}
	public   String getMaterialsByBaoxiuId(String id) throws JsonProcessingException {
		// TODO Auto-generated method stub
		System.out.println(id);
		XinyiPickingMapper mapper = sqlSession.getMapper(XinyiPickingMapper.class);
		XinyiPickingExample example = new XinyiPickingExample();
		com.xinyi.bean.XinyiPickingExample.Criteria createCriteria = example.createCriteria();
		createCriteria.andBaoxiuIdEqualTo(id);
		List<XinyiPicking> list = mapper.selectByExample(example);
		List<String> result = new ArrayList<String>();
		for(XinyiPicking picking:list) {
			result.add(picking.getFactMaterials());
		}
		return jsonCreater.writeValueAsString(result);
	}
	public   String getAllRecord() {
		XinyiPickingMapper pickingmapper = sqlSession.getMapper(XinyiPickingMapper.class);
		XinyiImportMapper importMapper = sqlSession.getMapper(XinyiImportMapper.class);
		List<XinyiPicking> pickingList =pickingmapper.selectAll();
		List<XinyiImport> importList = importMapper.selectAll();
		List<XinyiMaterial> MaterialList = new ArrayList<XinyiMaterial>();
		for(XinyiPicking pickItem :pickingList ) {
			if(pickItem.getFactMaterials() == null) {
				continue;
			}
			com.alibaba.fastjson.JSONArray.parse(pickItem.getFactMaterials());
		}
		// TODO Auto-generated method stub
		return null;
	}
	public String getAllImageInfo() throws JsonProcessingException {
		// TODO Auto-generated method stub
		return jsonCreater.writeValueAsString((imageMapper.selectAll()));
	}
	public String DeleteMaterialById(String id) {
		// TODO Auto-generated method stub
		XinyiMaterialMapper mapper = sqlSession.getMapper(XinyiMaterialMapper.class);
		try {
			mapper.deleteByPrimaryKey(id);
			sqlSession.commit();
			return "DeleteMaterialSucceed";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "DeleteMaterialFailed";
		}
	}
	public String modifyMaterialProperty(XinyiMaterial material) {
		// TODO Auto-generated method stub
		System.out.println(material.getMaterialId());
		XinyiMaterialMapper materialMapper = sqlSession.getMapper(XinyiMaterialMapper.class);
		try {
			materialMapper.updateByPrimaryKey(material);
			System.out.println(material.getViceId());
			return "modifyMaterialPropertySucceed";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "modifyMaterialPropertyfailed";
		}finally {
			sqlSession.commit();
		}
	}
	
	
	
	
}
