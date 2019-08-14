package com.study.xl.tymh.common;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @ClassName ServiceResult
 * @Description TODO
 * @Author xule
 * @Date 2019/8/7 15:11
 * @Version 1.0
 **/
@Data
public class ServiceResult extends JSONObject {

    private static final String SUCCESS = "success";
    private static final String CODE = "code";
    private static final String MSG = "msg";

    public Boolean isSuccess(){
        return this.getBoolean(SUCCESS);
    }



    public static ServiceResult success(){
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.put(SUCCESS, true);
        return serviceResult;
    }

    public static ServiceResult success(ServiceResultEnum serviceResultEnum) {
        ServiceResult success = success();
        success.put(CODE, serviceResultEnum.getCode());
        success.put(MSG, serviceResultEnum.getMsg());
        return success;
    }

    public static ServiceResult success(String code,String msg) {
        ServiceResult success = success();
        success.put(CODE, code);
        success.put(MSG, msg);
        return success;
    }

    public static ServiceResult fail(){
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.put(SUCCESS, false);
        return serviceResult;
    }

    public static ServiceResult fail(ServiceResultEnum serviceResultEnum) {
        ServiceResult fail = fail();
        fail.put(CODE, serviceResultEnum.getCode());
        fail.put(MSG, serviceResultEnum.getMsg());
        return fail;
    }

    public static ServiceResult fail(String code,String msg) {
        ServiceResult fail = fail();
        fail.put(CODE, code);
        fail.put(MSG, msg);
        return fail;
    }


}
