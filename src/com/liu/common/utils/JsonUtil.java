package com.liu.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**@Comments ：
 * @Author ：刘二冲
 * @Group : K组
 * @Worker: 1852
 * @Date ：2019年9月18日 上午10:56:09
 * @Project ：liuRCProject 
 * @Company ：Vstsoft
 */
public class JsonUtil {

	public static String getJsonStr(Object object) {
		
		
		return object.toString();
	}
	
	public static String toJSONString(Object object) {
        return JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
    }
	
	

}

