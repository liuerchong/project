package com.liu.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**@Comments ：
 * @Author ：刘二冲
 * @Group : K组
 * @Worker: 1852
 * @Date ：2019年9月25日 下午3:22:57
 * @Project ：liuRCProject 
 * @Company ：Vstsoft
 */
public class Log {

	
	private static Logger LOGGER = null;

    private static class SingletonHolder{
        public static Log instance = new Log();
    }

    private Log(){}

    public static Log getInstance(Class<?> clazz){
        LOGGER = LoggerFactory.getLogger(clazz);
        return SingletonHolder.instance;
    }

    public void info(String description, Object args, Object result) {
        LOGGER.info("线程ID: {}, 方法描述: {}, 调用参数: {}, 返回结果: {}", ThreadUtil.getThreadId(), description, JsonUtil.toJSONString(args), JsonUtil.toJSONString(result));
    }

    public void error(String description, Object args, Object result, Throwable t) {
        LOGGER.error("线程ID: {}, 方法描述: {}, 调用参数: {}, 返回结果: {}", ThreadUtil.getThreadId(), description, JsonUtil.toJSONString(args), JsonUtil.toJSONString(result), t);
    }
}

