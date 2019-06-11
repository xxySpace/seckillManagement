package com.xxy.seckill.seckillmanagement.service.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ClassName: DictModel
 * @Description: 系统字典模型
 * @Author: 13688
 * @Date: 2019/6/8 21:04
 * @Version: v1.0
 **/
public class DictModel {
    private Integer id;

    @NotBlank(message = "类别不能为空")
    private String catalog;

    @NotBlank(message = "值不能为空")
    private String value;

    @NotBlank(message = "名称不能为空")
    private String name;

    private String memo;

    @NotNull(message = "状态不能不填写")
    private Byte state;

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

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}
