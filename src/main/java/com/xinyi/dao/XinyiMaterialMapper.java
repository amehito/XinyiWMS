package com.xinyi.dao;

import com.xinyi.bean.XinyiMaterial;
import com.xinyi.bean.XinyiMaterialExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface XinyiMaterialMapper {
	
	
	List<XinyiMaterial> selectAll();
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table xinyi_material
	 * @mbg.generated  Wed Jul 03 14:05:28 CST 2019
	 */
	long countByExample(XinyiMaterialExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table xinyi_material
	 * @mbg.generated  Wed Jul 03 14:05:28 CST 2019
	 */
	int deleteByExample(XinyiMaterialExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table xinyi_material
	 * @mbg.generated  Wed Jul 03 14:05:28 CST 2019
	 */
	int deleteByPrimaryKey(String materialId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table xinyi_material
	 * @mbg.generated  Wed Jul 03 14:05:28 CST 2019
	 */
	int insert(XinyiMaterial record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table xinyi_material
	 * @mbg.generated  Wed Jul 03 14:05:28 CST 2019
	 */
	int insertSelective(XinyiMaterial record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table xinyi_material
	 * @mbg.generated  Wed Jul 03 14:05:28 CST 2019
	 */
	List<XinyiMaterial> selectByExample(XinyiMaterialExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table xinyi_material
	 * @mbg.generated  Wed Jul 03 14:05:28 CST 2019
	 */
	XinyiMaterial selectByPrimaryKey(String materialId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table xinyi_material
	 * @mbg.generated  Wed Jul 03 14:05:28 CST 2019
	 */
	int updateByExampleSelective(@Param("record") XinyiMaterial record, @Param("example") XinyiMaterialExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table xinyi_material
	 * @mbg.generated  Wed Jul 03 14:05:28 CST 2019
	 */
	int updateByExample(@Param("record") XinyiMaterial record, @Param("example") XinyiMaterialExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table xinyi_material
	 * @mbg.generated  Wed Jul 03 14:05:28 CST 2019
	 */
	int updateByPrimaryKeySelective(XinyiMaterial record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table xinyi_material
	 * @mbg.generated  Wed Jul 03 14:05:28 CST 2019
	 */
	int updateByPrimaryKey(XinyiMaterial record);
}