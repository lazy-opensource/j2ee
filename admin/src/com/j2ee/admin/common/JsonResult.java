package com.j2ee.admin.common;

import com.j2ee.admin.enums.ResultEnum;

import java.io.Serializable;

/**
 * Created by laizhiyuan on 2017/8/30.
 */
public class JsonResult implements Serializable{

    static final long serialVersionUID = -4986542L;

    public JsonResult() {
        super();
    }

    private String msg = ResultEnum.SUCCESS.getDesc();

    private Integer code = ResultEnum.SUCCESS.getCode();

    private boolean success = true;

    private Object result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        if (ResultEnum.SUCCESS.getCode() == code){
            this.success = true;
        }else {
            this.success = false;
        }
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
