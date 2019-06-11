package com.xxy.seckill.seckillmanagement.dao;

import com.xxy.seckill.seckillmanagement.dataobject.DictDAO;
import com.xxy.seckill.seckillmanagement.dataobject.DictDAOKey;
import com.xxy.seckill.seckillmanagement.service.model.DictModel;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;

@Component
public interface DictDAOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table code_dict
     *
     * @mbg.generated Sun Jun 09 16:03:28 CST 2019
     */
    int deleteByPrimaryKey(DictDAOKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table code_dict
     *
     * @mbg.generated Sun Jun 09 16:03:28 CST 2019
     */
    int insert(DictDAO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table code_dict
     *
     * @mbg.generated Sun Jun 09 16:03:28 CST 2019
     */
    int insertSelective(DictDAO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table code_dict
     *
     * @mbg.generated Sun Jun 09 16:03:28 CST 2019
     */
    DictDAO selectByPrimaryKey(DictDAOKey key);

    List<DictDAO> listDict(DictModel dictModel);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table code_dict
     *
     * @mbg.generated Sun Jun 09 16:03:28 CST 2019
     */
    int updateByPrimaryKeySelective(DictDAO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table code_dict
     *
     * @mbg.generated Sun Jun 09 16:03:28 CST 2019
     */
    int updateByPrimaryKey(DictDAO record);
}