package com.xxy.seckill.seckillmanagement.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ValidationResult
 * @Description: 数据校验结果类
 * @Author: 13688
 * @Date: 2019/5/4 0:02
 * @Version: v1.0
 **/
public class ValidationResult {
    /**
     * 校验结果是否有错
     */
    private boolean hasErrors = false;

    /**
     * 存放错误信息的map
     */
    private Map<String, String> errorMsgMap = new HashMap<>();

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }

    /**
     * 实现通用的通过格式化字符串信息获取错误结果的msg方法
     *
     * @return
     */
    public String getErrMsg() {
        return StringUtils.join(errorMsgMap.values().toArray(), ",");
    }
}
