package com.xxy.seckill.seckillmanagement.response;

/**
 * @ClassName: CommonRetrunType
 * @Description: 前端返回格式
 * @Author: 13688
 * @Date: 2019/4/28 23:48
 * @Version: v1.0
 **/
public class CommonRetrunType {
    /**
     * 表明对应请求的返回结果"success"或"fail"
     */
    private String status;

    /**
     * 若status=success,则data内返回前端需要的json数据
     * 若status=fail,则data内试用通用的错误码格式
     */
    private Object data;

    public static CommonRetrunType create(Object result) {
        return CommonRetrunType.create(result, "success");
    }

    public static CommonRetrunType create(Object result, String status) {
        CommonRetrunType type = new CommonRetrunType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
