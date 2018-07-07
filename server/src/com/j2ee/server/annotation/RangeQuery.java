package com.j2ee.server.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by laizhiyuan on 2017/9/1.
 *
 * <p>
 *     描述某个属性在查询时进行范围查找，需要额外反射当前属性值 + suffix 来获取
 * </p>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RangeQuery {

    /**
     * 可定义后缀，默认值是"2"
     * @return
     */
    public String suffix() default "2";
}
