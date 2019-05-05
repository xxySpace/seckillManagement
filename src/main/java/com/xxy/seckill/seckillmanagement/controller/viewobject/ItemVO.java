package com.xxy.seckill.seckillmanagement.controller.viewobject;

import java.math.BigDecimal;

/**
 * @ClassName: ItemVO
 * @Description: 前端显示信息，过滤掉重要信息
 * @Author: 13688
 * @Date: 2019/5/4 12:13
 * @Version: v1.0
 **/
public class ItemVO {
    /**
     * 商品编号
     */
    private Integer id;

    /**
     * 商品名
     */
    private String title;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品的库存
     */
    private Integer stock;

    /**
     * 商品的描述
     */
    private String description;

    /**
     * 商品的销量
     */
    private Integer sales;

    /**
     * 商品描述图片的url
     */
    private String imgUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
