package com.j2ee.server.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by laizhiyuan on 2017/9/1.
 *
 * <p>
 *     描述某个属性通过ReflectHelper工具类不给获取
 * </p>
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreReflect {
}
