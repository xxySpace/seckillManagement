package com.xxy.seckill.seckillmanagement.error;

/**
 * @ClassName: BusinessException
 * @Description: 定义业务异常类, 包装器业务异常实现
 * @Author: 13688
 * @Date: 2019/4/29 0:17
 * @Version: v1.0
 **/
public class BusinessException extends Exception implements CommonError {

    private CommonError commonError;

    /**
     * 直接接收EmBusinessError的传参用于构造业务异常
     *
     * @param commonError
     */
    public BusinessException(CommonError commonError) {
        super();
        this.commonError = commonError;
    }

    /**
     * 接收自定义errMsg的方式构造业务异常
     */
    public BusinessException(CommonError commonError, String errMsg) {
        super();
        this.commonError = commonError;
        this.commonError.setErrMsg(errMsg);
    }

    @Override
    public int getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.commonError.setErrMsg(errMsg);
        return this;
    }
}
