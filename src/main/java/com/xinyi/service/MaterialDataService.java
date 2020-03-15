package com.xinyi.service;


import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.xinyi.bean.*;
import org.apache.ibatis.session.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinyi.bean.XinyiActionExample.Criteria;
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
	@Autowired
	XinyiModifyhistoryMapper modifyhistoryMapper;
	@Autowired
	XinyiPickingMapper pickingMapper;
	@Autowired
	XinyiBatchStockMapper stockMapper;
	@Autowired
	XinyiImportMapper importMapper;
	public static ObjectMapper jsonCreater = new ObjectMapper();
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

//	static SqlSession sqlSession = MybatisOfSpringUtil.getSessionFactory().openSession();
//	static XinyiMaterialMapper materialMapper = materialMapper;
	@Autowired
	XinyiMaterialMapper materialMapper;
	public   String getMaterialInfo() throws JsonProcessingException {	
		ArrayList<XinyiMaterial> list = (ArrayList<XinyiMaterial>) materialMapper .selectAll();	
		jsonCreater.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		String result = jsonCreater.writeValueAsString(list);
	//	System.out.println("service"+result);
		return result; 
		
	}
	
	public   String getmodifyhistoryInfo() throws JsonProcessingException {
		// TODO Auto-generated method stub
		
		//XinyiModifyhistoryMapper mapper = sqlSession.getMapper(XinyiModifyhistoryMapper.class);
		ArrayList<XinyiModifyhistory> list = (ArrayList<XinyiModifyhistory>) modifyhistoryMapper.selectAll();	
		jsonCreater.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		String result = jsonCreater.writeValueAsString(list);
		return result;
	}
	public   String changematerialnumber(XinyiMaterial info) {
		System.out.println(info.getStockNumber());
		Double num =info.getStockNumber() ;
	//	XinyiMaterialMapper mapper = materialMapper;
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
	//	XinyiMaterialMapper mapper = materialMapper;
		try {
			materialMapper.insert(info);
		}catch (Exception e) {
			// TODO: handle exception
			materialMapper.updateByPrimaryKeySelective(info);
			result  = "已经存在该信息,以将值修改为最新的";
			System.out.println(e.toString());
		}
//		finally {
//			//sqlSession.commit();
//		}
		
		return result;
	}
	public   void addChangeHistory(XinyiMaterial info) {
		//这个方法用不到了，留着不删以防万一
		// TODO Auto-generated method stub
//		XinyiModifyhistoryMapper mapper = sqlSession.getMapper(XinyiModifyhistoryMapper.class);
		XinyiModifyhistory record = new XinyiModifyhistory();
		record.setMaterialid(info.getMaterialId());
		record.setMaterialname(info.getMaterialName());
//		record.setMaterialnumber(info.getStockNumber());
		record.setMaterialunit(info.getMaterialUnit());
		record.setModifymanager(info.getCreateManager());
		record.setModifyname("存入");
		record.setModifytime(new Date());
		modifyhistoryMapper.insert(record );
	//	//sqlSession.commit();
		
	}
	public   void savePickRequest(notifyModel notify) throws ParseException, JsonProcessingException {
		// TODO Auto-generated method stub
		try {
//			XinyiPickingMapper mapper = pickingMapper;
			XinyiPicking record = new XinyiPicking();
			System.out.println("admin:"+notify.getAdmin());
			record.setBaoxiuId(notify.getBaoxiuId());
			record.setName(notify.getAdmin());
			record.setTime(new Date());
			//picking表的username用在储存分厂上，现在不想改，以后有机会重构。
			record.setUsername(notify.getFengChang());
			System.out.println(notify.getTime()+"传入时间："+notify.getTime());
			String maString = jsonCreater.writeValueAsString(notify.getMaterials());
			record.setMaterials(maString);
			record.setPlus("未通过");
		//	record.setMaterials("测试");
			System.out.println("material:"+maString);

			pickingMapper.insertSelective(record);
		//	//sqlSession.commit();
			
			System.out.println("savePickRequest:");
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
	}
	public   ArrayList<XinyiPicking> getUncompletes() {
		// TODO Auto-generated method stub
		
	//	XinyiPickingMapper mapper = pickingMapper;
		
		XinyiPickingExample example = new XinyiPickingExample();
		com.xinyi.bean.XinyiPickingExample.Criteria criteria = example.createCriteria();
		criteria.andPlusEqualTo("未通过");
//		sqlSession.clearCache();
		
		ArrayList<XinyiPicking> list =(ArrayList<XinyiPicking>) pickingMapper.selectByExample(example );
		return list;
	}
	private void InitData(int id, ArrayList<Material> materials) {
		// TODO Auto-generated method stub
//		XinyiPickingMapper mapper = sqlSession.getMapper(XinyiPickingMapper.class);
		String data = pickingMapper.selectByPrimaryKey(id).getMaterials();
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
		XinyiPicking record = pickingMapper.selectByPrimaryKey(id);
		record.setMaterials(jsonArray.toJSONString());
		pickingMapper.updateByPrimaryKeySelective(record);
//		//sqlSession.commit();
		
	}
	public   boolean passRequest(int id,String admin, ArrayList<Material> materials) throws JsonProcessingException {
		// TODO Auto-generated method stub
		XinyiPickingMapper mapper = pickingMapper;
//	    XinyiBatchStockMapper stockMapper = stockMapper;
	 
	    //先修改传进来的值
		InitData(id,materials);

	    //
	    
	    
		
			List<Material> list = new ArrayList<Material>();
			XinyiPicking record = new XinyiPicking();
			record.setId(id);
			record.setPlus(admin+"通过");
			//修改材料表中的数量
//			XinyiMaterialMapper materialMapper = materialMapper;
			XinyiMaterial material;
			String data = mapper.selectByPrimaryKey(id).getMaterials();
			com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray.parseArray(data);
		    
			for(int i=0;i<jsonArray.size();i++) {
				com.alibaba.fastjson.JSONObject object = jsonArray.getJSONObject(i);
			    String materialId =	(String) object.get("materialId");   
			    double num = object.getDoubleValue("number");
			    material = materialMapper.selectByPrimaryKey(materialId);
			    double stockNum = material.getStockNumber();
			    material.setStockNumber(stockNum - num);
			    double materialTablePrice = material.getMaterialPrice();
			    materialMapper.updateByPrimaryKey(material);
			    
				//更改batchStock表数量
				XinyiBatchStockExample stockExample = new XinyiBatchStockExample();
			    com.xinyi.bean.XinyiBatchStockExample.Criteria criteria = stockExample.createCriteria();
			    criteria.andMaterialidEqualTo((String) object.get("materialId"));
			    List<XinyiBatchStock> stockList = stockMapper.selectByExample(stockExample);
			    double _num = num;
			   
			    double totalPrice = 0;
			    System.out.println(stockList.size());
			    
			    for(XinyiBatchStock item:stockList) {
			    	
			    	if(item.getNumber()==0 && stockList.size()>=2) {
			    		stockMapper.deleteByPrimaryKey(item.getId());
			    		continue;
			    	}
			    	
			    	if(item.getNumber() > _num) {
					    
			    		if(item.getPrice()<=0.01) {
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
//						//sqlSession.commit();

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
					    item.setNumber(0.0);
					    
			    		stockMapper.updateByPrimaryKeySelective(item);
//						//sqlSession.commit();

			    	}
			    }
			    if(_num!=0) {
			    	
			    	//_num不为0说明出库了库存里没收录的机物料，取平均价出库
			    	Material materialJson = new Material();
			    	
		    		
		    		
		    		materialJson.setTaxRate(material.getPlus());
				    materialJson.setManufacturing("未入库");
				    materialJson.setSupplier("未入库");
				    materialJson.setPrice(material.getMaterialPrice());
				    materialJson.setMaterialSpec(material.getMaterialSpec());
				    materialJson.setWarehousePosition(material.getWarehousePosition());
				    materialJson.setSize(material.getMaterialType());
				    materialJson.setTotalPrice(_num*material.getMaterialPrice());
				    materialJson.setMaterialId(materialId);
				    materialJson.setNumber(_num);
				    materialJson.setMaterial((String) object.get("material"));
				    materialJson.setUnit((String) object.get("unit"));
				    materialJson.setBatch("未入库");
				    list.add(materialJson);
//					//sqlSession.commit();
			    	
			    }
			    //将价格添加到json中
			    System.out.println("totalPrice"+totalPrice);
			    System.out.println("materialnum:"+num+"  _num:"+_num);
			   
			}
			
			String factJson = jsonCreater.writeValueAsString(list);
			record.setFactMaterials(factJson);
			mapper.updateByPrimaryKeySelective(record);
			//sqlSession.commit();
		    
		
		
		
		
		return true;
		
	}
	
	
	
	private   void updateBatchStock() {
		// TODO Auto-generated method stub
		
	}
	public   boolean declineRequest(int id, String admin) {
		// TODO Auto-generated method stub
		XinyiPickingMapper mapper = pickingMapper;
		try {
			XinyiPicking record = new XinyiPicking();
			record.setId(id);
			record.setPlus(admin+"拒绝");
			mapper.updateByPrimaryKeySelective(record);
			//sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			return false;
		}
		
		return true;
	}
	public   String getAllRequestInfo() throws JsonProcessingException {
		// TODO Auto-generated method stub
		XinyiPickingMapper mapper = pickingMapper;
		jsonCreater.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss"));
		String result = jsonCreater.writeValueAsString(mapper.selectAll());

		return result;
	}

	public String reIntoStock(List<XinyiImport> info) {
		for(XinyiImport i:info){
			//删除入库表数据
			XinyiImportExample example = new XinyiImportExample();
			XinyiImportExample.Criteria criteria = example.createCriteria();
			criteria.andBatchManageEqualTo(i.getBatchManage());
			criteria.andMaterialIdEqualTo(i.getMaterialId());
			criteria.andImportNumberEqualTo(i.getImportNumber());
			importMapper.deleteByExample(example);

			//修改库存

			XinyiBatchStockExample stockExample = new XinyiBatchStockExample();
			XinyiBatchStockExample.Criteria stockCri= stockExample.createCriteria();
			stockCri.andMaterialidEqualTo(i.getMaterialId());
			stockCri.andBatchEqualTo(i.getBatchManage());
			stockMapper.deleteByExample(stockExample);
		}

		return "delete Success";
	}

	public   String saveList(List<XinyiImport> info,HttpSession session) {
		// TODO Auto-generated method stub
		XinyiImportMapper mapper = importMapper;
		jsonCreater.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss"));
		try {
			for(XinyiImport item : info) {
				mapper.insert(item);
			}
			//sqlSession.commit();
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
//					material.setMaterialPrice(item.getPrice().floatValue());
					material.setMaterialPrice(item.getPriceIncludeTax().floatValue());
					material.setStockNumber(item.getImportNumber()*1.0);
					material.setStockSafe(0);
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
			//sqlSession.commit();
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
		XinyiBatchStockMapper mapper = stockMapper;
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
		//sqlSession.commit();
	}
	public  String getAllImportInfo() throws JsonProcessingException {
		// TODO Auto-generated method stub
		System.out.println(testMapper == null);
		testMapper.selectAll();
		XinyiImportMapper mapper = importMapper;
		List<XinyiImport> list  = mapper.selectAll();
		jsonCreater.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		String result = jsonCreater.writeValueAsString(list);
		return result;
	}
	public   String getMaterialsByBaoxiuId(String id) throws JsonProcessingException {
		// TODO Auto-generated method stub
		System.out.println(id);
		XinyiPickingMapper mapper = pickingMapper;
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
		XinyiPickingMapper pickingmapper = pickingMapper;
//		XinyiImportMapper importMapper = importMapper;
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
		XinyiMaterialMapper mapper = materialMapper;
		try {
			mapper.deleteByPrimaryKey(id);
			//sqlSession.commit();
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
//		XinyiMaterialMapper materialMapper = materialMapper;
		try {
			materialMapper.updateByPrimaryKey(material);
			System.out.println(material.getViceId());
			return "modifyMaterialPropertySucceed";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "modifyMaterialPropertyfailed";
		}finally {
			//sqlSession.commit();
		}
	}
	
	
	public String getLastOne() {
		XinyiImportMapper xinyiImportMapper = importMapper;
		return xinyiImportMapper.selectLastOne().getBatchManage();
	}


}
