package com.xinyi.test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xinyi.service.MaterialDataService;

public class notifyModel {
	private String baoxiuId;
	public String getBaoxiuId() {
		return baoxiuId;
	}
	public void setBaoxiuId(String baoxiuId) {
		this.baoxiuId = baoxiuId;
	}
	private String admin;
	private Date Time;
	private List<Material> Materials;

	
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public Date getTime() {
		return Time;
	}
	public void setTime(Date date) {
		Time = date;
	}
	public List<Material> getMaterials() {
		return Materials;
	}
	public void setMaterials(List<Material> materials) {
		Materials = materials;
	}
	
	
	
	
}
