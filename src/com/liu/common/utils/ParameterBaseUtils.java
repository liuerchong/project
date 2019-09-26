package com.liu.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**@Comments ：
 * @Author ：刘二冲
 * @Group : K组
 * @Worker: 1852
 * @Date ：2019年9月26日 下午4:02:25
 * @Project ：liuRCProject 
 * @Company ：Vstsoft
 */
public class ParameterBaseUtils {

	
	/**
     * 返回有的值条件,比如有个User类，有属性name,id
     * 当只有id有值时，返回and id ={id的值}
     *
     * @return
     * @throws
     * @author ld
     * @date 2019/02/11 14:13
     */
    public static  String gainConditionFromObjectByField(Object object) {

        StringBuffer sb = new StringBuffer();
        Field[] fields = object.getClass().getDeclaredFields();//取出所有属性
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            String fieldName = f.getName();
            Object value = getFieldValue(object, fieldName);//取属性值
            if (null!=value) {
                Class<?> type = f.getType();
                if ("class java.lang.String".equals(type.toString())) {
                    sb.append(" and " + fieldName + "='" + value + "'");
                } else {
                    sb.append(" and " + fieldName + "=" + value);
                }
            }
            continue;
        }
        return sb.toString();
    }


    private static String getFieldValue(Object owner, String fieldName) {
        Object o = invokeMethod(owner, fieldName, null);
        if (null!=o) {
            return o.toString();
        }
        return null;
    }


    /**
     * 执行某个Field的getField方法
     *
     * @param owner     类
     * @param fieldName 类的属性名称
     * @param args      参数，默认为null
     * @return
     */
    private static  Object invokeMethod(Object owner, String fieldName, Object[] args) {
        Class<? extends Object> ownerClass = owner.getClass();

        //fieldName -> FieldName
        String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

        Method method = null;
        try {
            method = ownerClass.getMethod("get" + methodName);
        } catch (SecurityException e) {

            //e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // e.printStackTrace();
            return "";
        }

        //invoke getMethod
        try {
            return method.invoke(owner);
        } catch (Exception e) {
            return "";
        }
    }
}

