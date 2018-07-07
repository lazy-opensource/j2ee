package com.j2ee.admin.enums;

/**
 * Created by laizhiyuan on 2017/8/30.
 */
public enum ResultEnum {

    SYS_ERROR(-1, "系统错误!"),
    PARAM_EMPTY(-2, "参数为空"),
    SUCCESS(0, "操作成功"),
    FAILD(1, "操作失败");

    private Integer code;

    private String desc;

    ResultEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
