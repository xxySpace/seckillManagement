package com.xxy.seckill.seckillmanagement.controller.viewobject;


import java.math.BigDecimal;

/**
 * @ClassName: PromoVO
 * @Description:  前端显示信息，过滤掉重要信息
 * @Author: 13688
 * @Date: 2019/6/21 16:37
 * @Version: v1.0
 **/
public class PromoVO {
    private Integer id;

    /**
     * 秒杀活动状态 1、未开始 2、进行中 3、已结束
     */
    private Integer status;

    /**
     * 秒杀活动名称
     */
    private String promoName;

    /**
     * 秒杀活动开始时间
     */
    private String startDate;

    /**
     * 秒杀活动的结束时间
     */
    private String endDate;

    /**
     * 秒杀活动的适用商品
     */
    private Integer itemId;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 秒杀活动的商品单价
     */
    private BigDecimal promoItemPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getPromoItemPrice() {
        return promoItemPrice;
    }

    public void setPromoItemPrice(BigDecimal promoItemPrice) {
        this.promoItemPrice = promoItemPrice;
    }
}
