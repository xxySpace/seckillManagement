package com.xxy.seckill.seckillmanagement.dao;

import com.xxy.seckill.seckillmanagement.dataobject.UserPasswordDAO;

public interface UserPasswordDAOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Apr 28 00:39:57 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Apr 28 00:39:57 CST 2019
     */
    int insert(UserPasswordDAO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Apr 28 00:39:57 CST 2019
     */
    int insertSelective(UserPasswordDAO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Apr 28 00:39:57 CST 2019
     */
    UserPasswordDAO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Apr 28 00:39:57 CST 2019
     */
    int updateByPrimaryKeySelective(UserPasswordDAO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Apr 28 00:39:57 CST 2019
     */
    int updateByPrimaryKey(UserPasswordDAO record);
}