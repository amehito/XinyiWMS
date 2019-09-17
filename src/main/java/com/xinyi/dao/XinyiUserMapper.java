package com.xinyi.dao;

import com.xinyi.bean.XinyiUser;
import com.xinyi.bean.XinyiUserExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
@Mapper
public interface XinyiUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xinyi_user
     *
     * @mbg.generated Fri May 24 23:04:22 CST 2019
     */
    long countByExample(XinyiUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xinyi_user
     *
     * @mbg.generated Fri May 24 23:04:22 CST 2019
     */
    int deleteByExample(XinyiUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xinyi_user
     *
     * @mbg.generated Fri May 24 23:04:22 CST 2019
     */
    int deleteByPrimaryKey(Integer userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xinyi_user
     *
     * @mbg.generated Fri May 24 23:04:22 CST 2019
     */
    int insert(XinyiUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xinyi_user
     *
     * @mbg.generated Fri May 24 23:04:22 CST 2019
     */
    int insertSelective(XinyiUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xinyi_user
     *
     * @mbg.generated Fri May 24 23:04:22 CST 2019
     */
    List<XinyiUser> selectByExample(XinyiUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xinyi_user
     *
     * @mbg.generated Fri May 24 23:04:22 CST 2019
     */
    XinyiUser selectByPrimaryKey(Integer userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xinyi_user
     *
     * @mbg.generated Fri May 24 23:04:22 CST 2019
     */
    int updateByExampleSelective(@Param("record") XinyiUser record, @Param("example") XinyiUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xinyi_user
     *
     * @mbg.generated Fri May 24 23:04:22 CST 2019
     */
    int updateByExample(@Param("record") XinyiUser record, @Param("example") XinyiUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xinyi_user
     *
     * @mbg.generated Fri May 24 23:04:22 CST 2019
     */
    int updateByPrimaryKeySelective(XinyiUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xinyi_user
     *
     * @mbg.generated Fri May 24 23:04:22 CST 2019
     */
    int updateByPrimaryKey(XinyiUser record);
}