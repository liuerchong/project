package com.liu.common.annotation;
/**@Comments ：
 * @Author ：刘二冲
 * @Group : K组
 * @Worker: 1852
 * @Date ：2019年9月18日 上午10:22:22
 * @Project ：liuRCProject 
 * @Company ：Vstsoft
 */
public @interface Log {

	 /** 要执行的操作类型比如：add操作 **/  
	 public String operationType() default "";  
	     
	 /** 要执行的具体操作比如：添加用户 **/  
	 public String operationName() default "";
}

