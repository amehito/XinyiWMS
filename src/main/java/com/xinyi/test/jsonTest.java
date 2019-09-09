package com.xinyi.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xinyi.bean.XinyiImport;
import com.xinyi.bean.XinyiMaterial;
import com.xinyi.bean.XinyiPicking;
import com.xinyi.dao.XinyiImportMapper;
import com.xinyi.dao.XinyiPickingMapper;
import com.xinyi.utils.MybatisOfSpringUtil;

public class jsonTest {
	SqlSession sqlSession = MybatisOfSpringUtil.getSessionFactory().openSession();

	@Test
	public void test() {
		
		XinyiPickingMapper pickingmapper = sqlSession.getMapper(XinyiPickingMapper.class);
		XinyiImportMapper importMapper = sqlSession.getMapper(XinyiImportMapper.class);
		List<XinyiPicking> pickingList =pickingmapper.selectAll();
		List<XinyiImport> importList = importMapper.selectAll();
		List<XinyiMaterial> MaterialList = new ArrayList<XinyiMaterial>();
		for(XinyiPicking pickItem :pickingList ) {
			if(pickItem.getFactMaterials() == null) {
				continue;
			}
			com.alibaba.fastjson.JSONArray parse = com.alibaba.fastjson.JSONArray.parseArray(pickItem.getFactMaterials());
			for(int i=0;i<parse.size();i++) {
				com.alibaba.fastjson.JSONObject object = parse.getJSONObject(i);			
				System.out.println("第"+i+"个："+object.get("materialId"));
			}
		}
	}
}
