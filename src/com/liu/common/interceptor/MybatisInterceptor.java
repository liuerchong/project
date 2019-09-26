package com.liu.common.interceptor;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;

/**@Comments ：
 * @Author ：刘二冲
 * @Group : K组
 * @Worker: 1852
 * @Date ：2019年9月26日 上午9:48:56
 * @Project ：liuRCProject 
 * @Company ：Vstsoft
 */
@Intercepts({  
	@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),  
    @Signature(type = Executor.class, method = "query",  args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) 
})
public class MybatisInterceptor implements Interceptor {

	private static final Logger log = LoggerFactory.getLogger(MybatisInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		 MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];  
	        Object parameter = null;  
	        if (invocation.getArgs().length > 1) {  
	            parameter = invocation.getArgs()[1];  
	        }  
 
	        BoundSql boundSql = mappedStatement.getBoundSql(parameter);  
	        Configuration configuration = mappedStatement.getConfiguration();  
	       
	        Object returnValue = null;  
	        //获取sql语句
	        String sql =showSql(configuration, boundSql); 
	        
	        log.info("Mybatis 拦截器获取SQL:{}",sql);
	        //获取数据源
	        DruidDataSource db = (DruidDataSource) configuration.getEnvironment().getDataSource();
	        
	         String pname="";
	         sql=sql.replaceAll("\'","").replace("\"", "");
	        // sql+=sql;
	         String sp = sql.toLowerCase().replace("call", "");
	         if(sp.startsWith("{")){
	        	int s = sp.indexOf("{")+1;
	        	int e = sp.indexOf("(");
	        	pname = sp.substring(s,e);
	         }
	       // System.out.println(sql);
	        String url = db.getUrl();
	        int s = url.lastIndexOf("/");
	        System.out.println(db.getUrl());
	        String updatestr = "update tbl_procedure set updatedate=NOW(),updator='czq',remark='"+sql+"',dbname='"+url.substring(s)+"'"+",pname='"+pname+"'"+" where redirect=";
	        //FileUtils.writeFile(updatestr,"d:/src/log.txt");
	        //FileUtils.writeStringToFile("d:/src/log.txt",updatestr , "UTF-8", true);
	        FileUtils.writeStringToFile(new File("d:/src/log.txt"), updatestr, "UTF-8", true);
	        //执行结果
	        returnValue = invocation.proceed();  
	        return returnValue;  
	}
 
	@Override
	public Object plugin(Object target) {
		// TODO Auto-generated method stub
		return Plugin.wrap(target, this);
	}
 
	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
	}
	
	
 private String getParameterValue(Object obj) {  
        String value = null;  
        if (obj instanceof String) {  
            value = "'" + obj.toString() + "'";  
    } else if (obj instanceof Date) {  
        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);  
        value = "'" + formatter.format(obj) + "'";  
//	            System.out.println(value);  
    } else {  
        if (obj != null) {  
            value = obj.toString();  
        } else {  
            value = "";  
            }  
   
        }  
        return value;  
    }  
	   
 public String showSql(Configuration configuration, BoundSql boundSql) {  
    Object parameterObject = boundSql.getParameterObject();  
    List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();  
    String sql = boundSql.getSql().replaceAll("[\\s]+", " ");  
    if (parameterMappings.size() > 0 && parameterObject != null) {  
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();  
        if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {  
            sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));  
   
            } else {  
                MetaObject metaObject = configuration.newMetaObject(parameterObject);  
                for (ParameterMapping parameterMapping : parameterMappings) {  
                    String propertyName = parameterMapping.getProperty();  
                    if (metaObject.hasGetter(propertyName)) {  
                        Object obj = metaObject.getValue(propertyName);  
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));  
                } else if (boundSql.hasAdditionalParameter(propertyName)) {  
                    Object obj = boundSql.getAdditionalParameter(propertyName);  
                    sql = sql.replaceFirst("\\?", getParameterValue(obj));  
                }  
            }  
        }  
    }  
    return sql;  
}

	/*public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];  
        Object parameter = null;  
        if (invocation.getArgs().length > 1) {  
            parameter = invocation.getArgs()[1];  
        }  

        BoundSql boundSql = mappedStatement.getBoundSql(parameter);  
    	Configuration configuration = mappedStatement.getConfiguration();  
    	Object returnVal = invocation.proceed();
   
    	//获取sql语句
   	 	String sql = getSql(configuration, boundSql);  
    	log.info("Mybatis 拦截器获取SQL:{}",sql);
    	return returnVal;
	}*/

	/**
	 * 获取SQL
	 * @param configuration
	 * @param boundSql
	 * @return
	 */
	private String getSql(Configuration configuration, BoundSql boundSql) {
		Object parameterObject = boundSql.getParameterObject();  
	    List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();  
	    String sql = boundSql.getSql().replaceAll("[\\s]+", " ");  
	    if (parameterObject == null || parameterMappings.size() == 0) {
	    	return sql;
	    }  
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();  
        if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {  
        	sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));  
        } else {  
        	MetaObject metaObject = configuration.newMetaObject(parameterObject);  
            for (ParameterMapping parameterMapping : parameterMappings) {  
            	String propertyName = parameterMapping.getProperty();  
                if (metaObject.hasGetter(propertyName)) {  
                	Object obj = metaObject.getValue(propertyName);  
                    sql = sql.replaceFirst("\\?", getParameterValue(obj));  
                } else if (boundSql.hasAdditionalParameter(propertyName)) {  
                	Object obj = boundSql.getAdditionalParameter(propertyName);  
                	sql = sql.replaceFirst("\\?", getParameterValue(obj));  
                }  
            }  
        }  
	    return sql;
	}

	/*private String getParameterValue(Object obj) {  
		String value = null;  
		if (obj instanceof String) {  
			value = "'" + obj.toString() + "'";  
	    } else if (obj instanceof Date) {  
	        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);  
	        value = "'" + formatter.format(obj) + "'";  
	    } else {  
	        if (obj != null) {  
	            value = obj.toString();  
	        } else {  
	            value = "";  
	        }  
	    }  
	    return value;  
	}*/

	
}

