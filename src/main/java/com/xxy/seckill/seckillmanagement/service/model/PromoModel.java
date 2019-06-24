package com.xxy.seckill.seckillmanagement.service.model;

import org.joda.time.DateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: PromoModel
 * @Description: 秒杀营销模型
 * @Author: 13688
 * @Date: 2019/5/13 22:33
 * @Version: v1.0
 **/
public class PromoModel {
    private Integer id;

    /**
     * 秒杀活动状态 1、未开始 2、进行中 3、已结束
     */
    private Integer status;

    /**
     * 秒杀活动名称
     */
    @NotBlank(message = "活动名称不能为空")
    private String promoName;

    /**
     * 秒杀活动开始时间
     */
    private DateTime startDate;

    /**
     * 秒杀活动的结束时间
     */
    private DateTime endDate;

    /**
     * 秒杀活动的适用商品
     */
    @NotNull(message = "活动商品编码不能为空")
    private Integer itemId;

    /**
     * 秒杀活动的商品单价
     */
    @NotNull(message = "商品活动价格不能为空")
    @Min(value = 0, message = "商品活动价格必须大于0")
    private BigDecimal promoItemPrice;

    /** 查询条件*/

    /**
     * 秒杀活动的开始时间区间
     */
    private Date startDateBegin;

    private Date startDateEnd;

    /**
     * 秒杀活动的结束时间区间
     */
    private Date endDateBegin;

    private Date endDateEnd;

    /**
     * 商品名称
     */
    private String itemName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getPromoItemPrice() {
        return promoItemPrice;
    }

    public void setPromoItemPrice(BigDecimal promoItemPrice) {
        this.promoItemPrice = promoItemPrice;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartDateBegin() {
        return startDateBegin;
    }

    public void setStartDateBegin(Date startDateBegin) {
        this.startDateBegin = startDateBegin;
    }

    public Date getStartDateEnd() {
        return startDateEnd;
    }

    public void setStartDateEnd(Date startDateEnd) {
        this.startDateEnd = startDateEnd;
    }

    public Date getEndDateBegin() {
        return endDateBegin;
    }

    public void setEndDateBegin(Date endDateBegin) {
        this.endDateBegin = endDateBegin;
    }

    public Date getEndDateEnd() {
        return endDateEnd;
    }

    public void setEndDateEnd(Date endDateEnd) {
        this.endDateEnd = endDateEnd;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
