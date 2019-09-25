package com.liu.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.liu.common.enums.BackupTypeEnum;

/**@Comments ：
 * @Author ：刘二冲
 * @Group : K组
 * @Worker: 1852
 * @Date ：2019年9月25日 下午2:13:17
 * @Project ：liuRCProject 
 * @Company ：Vstsoft
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ServiceLog {

	
	/**
	* 当前操作组别
	* 一般以 Controller 的 RequestMapping命名
	* @return
	*/
	    String actionGroup() default "";
	    
	    /**
	     * 操作类型
	     * 一般定义/create, /update, /delete分别表示是新增，修改，删除
	     * @return
	     */
	    String actionType() default "";
	    
	    /**
	     * 操作描述
	     * @return
	     */
	    String actionDesc() default "";
	  
	    /**
	     * 是否插入该条操做纪录至数据库
	     * @return
	     */
	    boolean insertDb() default false;


	    BackupTypeEnum backupType() default BackupTypeEnum.COMMON_BACKUP;
	    
	    /**
	     * 插入纪录时 在HttpServletRequest中的key
	     * @return
	     */
	    String primaryKey() default "id";
	    
	    
	    /**
	     * 插入纪录时 所需要调用的注解到SPRING中的service bean name， 通过该类去数据库查找当前操做数据的原始记录 以及操做完后的值
	     * @return
	     */
	    String serviceBeanClassName() default "";
	   
	    /**
	     * 根据primaryKey 在service层掉用查数据库的方法名
	     * @return
	     */
	    String findByPrimaryKeyMethodName() default "findByPrimaryKey";
	    
	    /**
	     * 操作人在的HttpServletRequest中的key(对于非后台请求我们没法中CAS中查到操做人， 所以需要在controller的请求方法中传递给属性)
	     * @return
	     */
	    String operatorKey() default "operator";


}

