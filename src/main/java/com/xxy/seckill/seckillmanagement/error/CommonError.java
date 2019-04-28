package com.xxy.seckill.seckillmanagement.error;

/**
 * @ClassName: CommonError
 * @Description: 公共错误信息接口
 * @Author: 13688
 * @Date: 2019/4/29 0:00
 * @Version: v1.0
 **/
public interface CommonError {
    int getErrCode();

    String getErrMsg();

    CommonError setErrMsg(String errMsg);
}
