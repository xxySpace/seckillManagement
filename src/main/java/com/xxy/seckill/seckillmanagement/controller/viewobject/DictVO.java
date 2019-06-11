package com.xxy.seckill.seckillmanagement.controller.viewobject;

/**
 * @ClassName: DictVO
 * @Description: 前端显示信息，过滤掉重要信息
 * @Author: 13688
 * @Date: 2019/6/10 18:48
 * @Version: v1.0
 **/
public class DictVO {
    private Integer id;

    private String catalog;

    private String value;

    private String name;

    private String memo;

    private String state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
