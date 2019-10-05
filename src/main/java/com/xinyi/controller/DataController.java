package com.xinyi.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinyi.bean.XinyiImport;
import com.xinyi.bean.XinyiMaterial;
import com.xinyi.bean.XinyiPicking;
import com.xinyi.service.MaterialDataService;
import com.xinyi.test.Material;
import com.xinyi.test.notifyModel;

@Controller
@RequestMapping("/Material")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DataController {
	@Autowired
	MaterialDataService materialDataService;
	
	
	notifyModel notify;
	final static int  materialInfoNumber = 5;
	boolean needQuery = false;
	static boolean firstLogin = true;
	static final String NO_MESSAGE = "no message";
	ArrayList<notifyModel> list = new ArrayList<notifyModel>();
	public static ObjectMapper jsonCreater = new ObjectMapper();
	ArrayList<XinyiPicking> uncompleteList;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");

    
    @RequestMapping(value="/ImageInfo",produces="application/json;charset=utf-8")
    public @ResponseBody String getImageInfo() throws JsonProcessingException {
    	return materialDataService.getAllImageInfo();
    	
    }
    @RequestMapping(value="/importMaterials",produces="application/json;charset=utf-8")
	public @ResponseBody String importMateriasls(@RequestBody List<XinyiImport>info,HttpSession session) throws JsonProcessingException {
    	String result =materialDataService.saveList(info,session);	
    	return result;
    }
    
    @RequestMapping(value="/allRecord",produces="application/json;charset=utf-8")
	public @ResponseBody String getAllRecord() throws JsonProcessingException {
    	String result =materialDataService.getAllRecord();	
    	return result;
    }
    
    @RequestMapping(value="/allImportInfo",produces="application/json;charset=utf-8")
    public @ResponseBody String ImportInfo() throws JsonProcessingException {
    	String result = materialDataService.getAllImportInfo();
    	return result;
    }
	
	@RequestMapping(value="/notificationInit",produces="application/json;charset=utf-8")
	public @ResponseBody String notificationInit() throws JsonProcessingException {
		uncompleteList = materialDataService.getUncompletes();
		System.out.println(uncompleteList.size());
		if(uncompleteList ==null) {
			return NO_MESSAGE;
		}
		ArrayList<XinyiPicking> theList =  (ArrayList<XinyiPicking>) uncompleteList.clone();
		jsonCreater.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh-mm-ss"));
		String result = jsonCreater.writeValueAsString(theList);
		return result;
	}
	
	
	@RequestMapping(value="/declineRequest",produces="application/json;charset=utf-8")
	public @ResponseBody String declineRequest(HttpServletRequest request,@RequestBody  String id,HttpSession session)  {
		System.out.println(request.getParameterMap().entrySet().size());
		System.out.println("id"+id);
		String admin = (String) session.getAttribute("UserName");
		if(!materialDataService.declineRequest(Integer.parseInt(id),admin)) {
			return"PassFailure";
		}
		changeNotifyState();
		return "拒绝成功";
	}
	
	@RequestMapping(value="/passRequest",produces="application/json;charset=utf-8")
	public @ResponseBody String passRequest(HttpSession session,@RequestParam(value="globalId") String id,HttpServletRequest request)  {
		String admin = (String) session.getAttribute("UserName");
		Map<String,String[]> params =  request.getParameterMap();
	    ArrayList<Material> materials = new ArrayList<Material>();
	    System.out.println(params.size());
	    for(int i=0;i<params.size()/2;i++) {       	     
        	Material material = new Material();
        	material.setMaterialId(params.get("materialInfo["+i+"][materialId]")[0]);
        	material.setNumber(Integer.parseInt(params.get("materialInfo["+i+"][number]")[0]));
        	materials.add(material);
        }
	    if(!materialDataService.passRequest(Integer.parseInt(id),admin,materials)) {
			return"出库失败";
		}
		changeNotifyState();
		
		return "出库成功";
	}
	
	@RequestMapping(value="allRequestInfo",produces="application/json;charset=utf-8")
	public @ResponseBody String allRequestInfo() throws JsonProcessingException{
		String result = materialDataService.getAllRequestInfo();
		System.out.println(new Date().toString()+"allRequestInfo");
		return result;
	}
	
	@RequestMapping(value="/showNotification",produces="application/json;charset=utf-8")
	public @ResponseBody String show() throws JsonProcessingException {
//		 =  jsonCreater.writeValueAsString(list);
		//第一次登陆也查询所有
//		if(firstLogin) {
//			changeNotifyState();
//			firstLogin = false;
//		}
		if(!needQuery) {
			return NO_MESSAGE;
		}
		//每一个连接都有一个独立的list
		ArrayList<XinyiPicking> theList =  (ArrayList<XinyiPicking>) uncompleteList.clone();
		String result = jsonCreater.writeValueAsString(theList);
		
		needQuery = false;
		
		System.out.println(list.size());
		return result;
	}
	
	@RequestMapping(value="/materialsById",produces="application/json;charset=utf-8")
	public @ResponseBody String materialsById(String id) throws JsonProcessingException {
		return materialDataService.getMaterialsByBaoxiuId(id);
	}
	
	@RequestMapping(value="/notify",produces="application/json;charset=utf-8")
	@ResponseBody
	public String Notice(@RequestParam(value="materials[0][materialId]",required = false) String info,
            HttpServletRequest request) throws JsonProcessingException, ParseException {
		if(info == null)
			return "notice";
		System.out.println(info);
	//	System.out.println(info.getAdmin());
	//	System.out.println(info.getMaterials());
		System.out.println("获取单个参数：");
        System.out.println(info);
        System.out.println("获取所有参数：");	
        Map<String,String[]> params =  request.getParameterMap();
        for(Map.Entry<String,String[]> entry:params.entrySet()){
            System.out.println("key:"+entry.getKey()+" value:"+ Arrays.asList(entry.getValue()));
            
        }
        
        notify = new notifyModel();
        try {
        	notify.setBaoxiuId(params.get("baoxiu_id")[0]);
        }catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
        System.out.println("baoxiu_id"+notify.getBaoxiuId());
        notify.setAdmin(params.get("user")[0]);
        notify.setTime(format.parse(params.get("Time")[0]));
 //       System.out.println(notify.getAdmin());
//        System.out.println(notify.getTime());
        
//        params.remove("user");
//        params.remove("Time");
        System.out.println(params.size());
        ArrayList<Material> materialList = new ArrayList<Material>();
        for(int i=0;i<params.size()/materialInfoNumber;i++) {       	     
        	Material material = new Material();
        	material.setMaterialId(params.get("materials["+i+"][materialId]")[0]);
        	material.setMaterial(params.get("materials["+i+"][material]")[0]);
        	material.setNumber(Integer.parseInt(params.get("materials["+i+"][number]")[0]));
        	material.setUnit(params.get("materials["+i+"][unit]")[0]);
        	try {
            	material.setWarehousePosition(params.get("materials["+i+"][warehousePosition]")[0]);

        	}catch (Exception e) {
				// TODO: handle exception
        		e.printStackTrace();
			}
        	materialList.add(material);
        }
        notify.setMaterials(materialList);
        materialDataService.savePickRequest(notify);
        list.add(notify);
        changeNotifyState();
		return "success";
		
	}
	
	private void changeNotifyState() {
		// TODO Auto-generated method stub
		needQuery = true;
		//得到数据库中未完成的元组，其中plus存放是否完成
		uncompleteList = materialDataService.getUncompletes();
		
	} 

	@RequestMapping(value="/getMaterialInfo",produces="application/json;charset=utf-8")
	@ResponseBody
	public String getMaterialInfo() throws JsonProcessingException {
		System.out.println(new Date().toString()+"start:getMaterialInfo");
		String result =  materialDataService.getMaterialInfo();
		System.out.println(new Date().toString()+"done:getMaterialInfo");
		return result;
	}
	
	@RequestMapping(value="/getmodifyhistory",produces="application/json;charset=utf-8")
	@ResponseBody
	public String getmodifyhistory() throws JsonProcessingException {
		System.out.println(new Date().toString()+"start:getModifyhistory");
		String result =  materialDataService.getmodifyhistoryInfo();
		
		System.out.println(new Date().toString()+"done:getmodifyhistory");
		return result;
	}
	
	
	@RequestMapping(value="/changematerialnumber",produces="application/json;charset=utf-8")
	@ResponseBody
	public String changematerialnumber(XinyiMaterial info) throws JsonProcessingException {
		
		System.out.println("info: "+info.toString());
		String result =  materialDataService.changematerialnumber(info);
		System.out.println("controlresult"+result);
		return result;
	}
	
	@RequestMapping(value="/addmaterial",produces="application/json;charset=utf-8")
	@ResponseBody
	public String Addmaterial(XinyiMaterial info) throws JsonProcessingException {
		
		String result =  materialDataService.addMaterial(info);
		materialDataService.addChangeHistory(info);
		System.out.println("controlresult"+result);
		return result;
	}
	
	
}
