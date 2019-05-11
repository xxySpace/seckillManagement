package com.xxy.seckill.seckillmanagement.dao;

import com.xxy.seckill.seckillmanagement.dataobject.ItemStockDAO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface ItemStockDAOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sat May 04 11:21:13 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sat May 04 11:21:13 CST 2019
     */
    int insert(ItemStockDAO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sat May 04 11:21:13 CST 2019
     */
    int insertSelective(ItemStockDAO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sat May 04 11:21:13 CST 2019
     */
    ItemStockDAO selectByPrimaryKey(Integer id);

    ItemStockDAO selectByItemId(Integer itemId);

    int decreaseStock(@Param("itemId") Integer itemId, @Param("amount") Integer amount);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sat May 04 11:21:13 CST 2019
     */
    int updateByPrimaryKeySelective(ItemStockDAO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sat May 04 11:21:13 CST 2019
     */
    int updateByPrimaryKey(ItemStockDAO record);
}