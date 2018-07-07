package com.j2ee.server.utils;

import com.j2ee.server.annotation.IgnoreReflect;
import com.j2ee.server.annotation.RangeQuery;
import com.j2ee.server.orm.TDemoEntity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by laizhiyuan on 2017/8/31.
 */
public abstract class ReflectHelper {

    /**
     * 3级父类查找
     * @param fieldName
     * @param clazz
     * @return
     */
    public static Field getFieldByName(String fieldName, Class clazz){
        if (clazz == null || StringHelper.isEmpty(fieldName)){
            return null;
        }
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            try {
                field = clazz.getSuperclass().getDeclaredField(fieldName);
            } catch (NoSuchFieldException e1) {
                try {
                    field = clazz.getSuperclass().getSuperclass().getDeclaredField(fieldName);
                } catch (NoSuchFieldException e2) {
                    e2.printStackTrace();
                }
            }
        }
        if (isIgnoreReflect(field)){
            return null;
        }
        return field;
    }

    /**
     * get method
     * @param field
     * @param clazz
     * @return
     */
    public static Method getGetMethodByField(Field field, Class clazz){
        if (clazz == null || field == null){
            return null;
        }
        String fieldName = field.getName();
        Method method = getGetMethodByFieldName(fieldName, clazz);
        return method;
    }

    public static Method getGetMethodByFieldName(String fieldName, Class clazz){
        if (clazz == null || StringHelper.isEmpty(fieldName)){
            return null;
        }
        String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        try {
            Method method = clazz.getMethod(methodName, new Class[]{});
            return method;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isIgnoreReflect(Field field){
        if (field == null){
            return true;
        }
        Annotation annotation = field.getAnnotation(IgnoreReflect.class);
        if (annotation == null){
            return false;
        }
        return true;
    }

    public static boolean isRangeQuery(Field field){
        if (field == null){
            return false;
        }
        Annotation annotation = field.getAnnotation(RangeQuery.class);
        if (annotation == null){
            return false;
        }
        return true;
    }

    /**
     * set method
     * @param field
     * @param clazz
     * @return
     */
    public static Method getSetMethodByField(Field field, Class clazz){
        if (clazz == null || field == null){
            return null;
        }
        String fieldName = field.getName();
        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        try {
            Method method = clazz.getMethod(methodName, field.getType());
            return method;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获得所有属性，不包括父类
     * @param clazz
     * @return
     */
    public static List<String> fields(Class clazz){
        if (clazz == null){
            return null;
        }
        List<String> fields = new ArrayList<String>();
        Field[] listField = clazz.getDeclaredFields();
        if (listField != null){
            for (int i = 0; i < listField.length; i++){
                if (!isIgnoreReflect(listField[i])){
                    fields.add(listField[i].getName());
                }
            }
        }
        return fields;
    }

    /**
     * 获得所有属性，包括所有父类的
     * @param clazz
     * @return
     */
    public static List<String> allFields(Class clazz){
        if (clazz == null){
            return null;
        }
        List<String> fields = new ArrayList<String>();
        while (clazz != null){
            fields.addAll(fields(clazz));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    /**
     * set vlaue to field
     * @param fieldName
     * @param value
     * @param obj
     */
    public static void setFieldValue(String fieldName, Object value, Object obj){
        String typeName = null;
        try {
            if (StringHelper.isEmpty(fieldName) || obj == null || value == null){
                return;
            }
            Class clazz = obj.getClass();
            Field field = getFieldByName(fieldName, clazz);
            Method method = getSetMethodByField(field, clazz);
            typeName = field.getType().getSimpleName();
            if ("String".equals(typeName)){
                method.invoke(obj, value.toString());
            }else if ("Boolean".equals(typeName)){
                method.invoke(obj, Boolean.valueOf(value.toString()));
            }else if ("Integer".equals(typeName)){
                method.invoke(obj, Integer.valueOf(value.toString()));
            }else if ("Date".equals(typeName)){
                method.invoke(obj, Date.valueOf(value.toString()));
            }else if ("Timestamp".equals(typeName)){
                method.invoke(obj, Timestamp.valueOf(value.toString()));
            }else if ("Time".equals(typeName)){
                method.invoke(obj, Time.valueOf(value.toString()));
            }else if ("Double".equals(typeName)){
                method.invoke(obj, Double.valueOf(value.toString()));
            }else if ("Float".equals(typeName)){
                method.invoke(obj, Float.valueOf(value.toString()));
            }else if ("Long".equals(typeName)){
                method.invoke(obj, Long.valueOf(value.toString()));
            }else if ("Short".equals(typeName)){
                method.invoke(obj, Short.valueOf(value.toString()));
            }else if ("Byte".equals(typeName)){
                method.invoke(obj, Byte.valueOf(value.toString()));
            }else if ("Character".equals(typeName)){
                method.invoke(obj, Character.valueOf((Character) value));
            }else if ("BigDecimal".equals(typeName)){
                method.invoke(obj, BigDecimal.valueOf(Long.valueOf(value.toString())));
            }else {
                method.invoke(obj, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("====================> 不支持类型:" + typeName);
        }
    }

    /**
     * 获得父类泛型实例
     * @return
     */
    public static Object getGenericSuperclass(Class clazz){
        if (clazz == null){
            return null;
        }
        Type type=clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType)type;
            Class c =(Class) parameterizedType.getActualTypeArguments()[0];
            try {
                return c.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        TDemoEntity demoEntity = new TDemoEntity();
        Object timestamp = "2017-08-31 13:39:12.0";
        setFieldValue("createTime", timestamp, demoEntity);
    }
}
