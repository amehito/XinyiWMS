package com.xinyi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xinyi.bean.XinyiImport;
import com.xinyi.bean.XinyiImportExample;


@Mapper
public interface XinyiImportMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table xinyi_import
	 * @mbg.generated  Sat Jul 27 13:57:14 CST 2019
	 */
	long countByExample(XinyiImportExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table xinyi_import
	 * @mbg.generated  Sat Jul 27 13:57:14 CST 2019
	 */
	int deleteByExample(XinyiImportExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table xinyi_import
	 * @mbg.generated  Sat Jul 27 13:57:14 CST 2019
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table xinyi_import
	 * @mbg.generated  Sat Jul 27 13:57:14 CST 2019
	 */
	int insert(XinyiImport record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table xinyi_import
	 * @mbg.generated  Sat Jul 27 13:57:14 CST 2019
	 */
	int insertSelective(XinyiImport record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table xinyi_import
	 * @mbg.generated  Sat Jul 27 13:57:14 CST 2019
	 */
	List<XinyiImport> selectByExample(XinyiImportExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table xinyi_import
	 * @mbg.generated  Sat Jul 27 13:57:14 CST 2019
	 */
	XinyiImport selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table xinyi_import
	 * @mbg.generated  Sat Jul 27 13:57:14 CST 2019
	 */
	int updateByExampleSelective(@Param("record") XinyiImport record, @Param("example") XinyiImportExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table xinyi_import
	 * @mbg.generated  Sat Jul 27 13:57:14 CST 2019
	 */
	int updateByExample(@Param("record") XinyiImport record, @Param("example") XinyiImportExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table xinyi_import
	 * @mbg.generated  Sat Jul 27 13:57:14 CST 2019
	 */
	int updateByPrimaryKeySelective(XinyiImport record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table xinyi_import
	 * @mbg.generated  Sat Jul 27 13:57:14 CST 2019
	 */
	int updateByPrimaryKey(XinyiImport record);
	
	public List<XinyiImport> selectAll();
}