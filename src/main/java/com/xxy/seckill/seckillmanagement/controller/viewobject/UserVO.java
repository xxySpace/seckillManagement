package com.xxy.seckill.seckillmanagement.controller.viewobject;

/**
 * @ClassName: UserVO
 * @Description: 前端显示信息，过滤掉重要信息
 * @Author: 13688
 * @Date: 2019/4/28 22:33
 * @Version: v1.0
 **/
public class UserVO {
    private Integer id;
    private String name;
    private String gender;
    private Integer age;
    private String telphone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
}
