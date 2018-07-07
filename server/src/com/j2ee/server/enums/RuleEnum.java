package com.j2ee.server.enums;

/**
 * Created by lzy on 2017/8/27.
 *
 * <p>
 *     查询规则枚举
 * </p>
 */
public enum RuleEnum {

    LE("<="),GE(">="),EQ("="),LT("<"),GT(">"),IN("in"),NOT_NULL("notNull"),
    LIKE("like"),IS_NULL("isNull"),NOT_IN("notIn"),NOT_EQ("notEq"),BETWEEN("between");

    private String rule;

    RuleEnum(String rule) {
        this.rule = rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getRule() {
        return rule;
    }

    public static RuleEnum searchByRule(String rule){
        for (RuleEnum ruleEnum : RuleEnum.values()){
            if (ruleEnum.getRule().equalsIgnoreCase(rule)){
                return ruleEnum;
            }
        }
        System.out.println("=======================> Not Found Match " + rule + " in RuleEnum.values");
        return RuleEnum.EQ;
    }
}
