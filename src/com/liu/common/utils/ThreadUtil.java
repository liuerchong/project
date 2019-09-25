package com.liu.common.utils;

import java.util.UUID;

/**@Comments ：
 * @Author ：刘二冲
 * @Group : K组
 * @Worker: 1852
 * @Date ：2019年9月25日 下午3:13:32
 * @Project ：liuRCProject 
 * @Company ：Vstsoft
 */
public class ThreadUtil {

	
	 private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

	    public static String getThreadId() {
	        String threadId = threadLocal.get();
	        if (null == threadId) {
	            threadId = UUID.randomUUID().toString();
	            threadLocal.set(threadId);
	        }
	        return threadId;
	    }
}

