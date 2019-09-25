package com.liu.common.aspact;


import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.liu.common.annotation.SystemControllerLog;
import com.liu.common.annotation.SystemServiceLog;
import com.liu.common.enums.AnnotationTypeEnum;
import com.liu.common.utils.JsonUtil;
import com.liu.common.utils.ThreadUtil;
import com.liu.model.log.SystemLogStrategy;

/**@Comments ：
 * @Author ：刘二冲
 * @Group : K组
 * @Worker: 1852
 * @Date ：2019年9月25日 下午3:09:06
 * @Project ：liuRCProject 
 * @Company ：Vstsoft
 */
@Aspect
@Component
public class LiuProjectLogAspect {

	
	public LiuProjectLogAspect(){
		System.out.println(">>>>>>>>>>>>>>>>>>>>LiuProjectLogAspect实例化");
	}
	//private static final Logger LOG = LoggerFactory.getLogger(SystemLogAspect.class);

    private static final Logger LOG = LoggerFactory.getLogger(SystemLogAspect.class);

    @Pointcut("(execution(* com.liu.service..*(..))||execution(* com.liu.controller..*(..)) )&& !execution(* com.liu..*.log..*(..))")
    public void pointcut() {

    }
    
    /** 
	 * 前置通知 用于拦截Controller层记录用户的操作 
	 * 
	 * @param joinPoint 切点 
	 */ 
	@Before("pointcut()")
	public void doBefore(JoinPoint joinPoint) { //传入连接点对象
	    System.out.println("==========执行controller前置通知===============");
	    if(LOG.isInfoEnabled()){
	    	LOG.info("before " + joinPoint);
	    }
	}    

	//配置controller环绕通知,使用在方法aspect()上注册的切入点
	  @Around("pointcut()")
	  public void around(JoinPoint joinPoint){
	      System.out.println("==========开始执行controller环绕通知===============");
	      long start = System.currentTimeMillis();
	      try {
	          ((ProceedingJoinPoint) joinPoint).proceed();
	          long end = System.currentTimeMillis();
	          if(LOG.isInfoEnabled()){
	        	  LOG.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
	          }
	          System.out.println("==========结束执行controller环绕通知===============");
	      } catch (Throwable e) {
	          long end = System.currentTimeMillis();
	          if(LOG.isInfoEnabled()){
	        	  LOG.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
	          }
	      }
	  }
    @Around("pointcut()")
    public Object doInvoke(ProceedingJoinPoint pjp) {
        long start = System.currentTimeMillis();

        Object result = null;

        try {
            result = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            LOG.error(throwable.getMessage(), throwable);
            throw new RuntimeException(throwable);
        } finally {
            long end = System.currentTimeMillis();
            long elapsedTime = end - start;

            printLog(pjp, result, elapsedTime);

        }

        return result;
    }

    /**
     * 打印日志
     * @param pjp   连接点
     * @param result    方法调用返回结果
     * @param elapsedTime   方法调用花费时间
     */
    private void printLog(ProceedingJoinPoint pjp, Object result, long elapsedTime) {
    	
       final SystemLogStrategy strategy = getFocus(pjp);

        if (null != strategy) {
            strategy.setThreadId(ThreadUtil.getThreadId());
            strategy.setResult(JsonUtil.toJSONString(result));
            strategy.setElapsedTime(elapsedTime);
            if (strategy.isAsync()) {
            	new Thread(new Runnable() {
					
					@Override
					public void run() {
						LOG.info(strategy.format(), strategy.args());
					}
				}
            	).start();
                //new Thread(()->LOG.info(strategy.format(), strategy.args())).start();
            }else {
                LOG.info(strategy.format(), strategy.args());
            }
        }
    }

    /**
     * 获取注解
     */
    private SystemLogStrategy getFocus(ProceedingJoinPoint pjp) {
        Signature signature = pjp.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();
        Object[] args = pjp.getArgs();
        String targetClassName = pjp.getTarget().getClass().getName();
        try {
            Class<?> clazz = Class.forName(targetClassName);
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (methodName.equals(method.getName())) {
                	//method.getParameterAnnotations().length;
                    //if (args.length == method.getParameterCount()) {

                        SystemLogStrategy strategy = new SystemLogStrategy();
                        strategy.setClassName(className);
                        strategy.setMethodName(methodName);

                        SystemControllerLog systemControllerLog = method.getAnnotation(SystemControllerLog.class);
                        if (null != systemControllerLog) {
                            strategy.setArguments(JsonUtil.toJSONString(args));
                            strategy.setDescription(systemControllerLog.description());
                            strategy.setAsync(systemControllerLog.async());
                            strategy.setLocation(AnnotationTypeEnum.CONTROLLER.getName());
                            return strategy;
                        }
                        SystemServiceLog systemServiceLog = method.getAnnotation(SystemServiceLog.class);
                        if (null != systemServiceLog) {
                            strategy.setArguments(JsonUtil.toJSONString(args));
                            strategy.setDescription(systemServiceLog.description());
                            strategy.setAsync(systemServiceLog.async());
                            strategy.setLocation(AnnotationTypeEnum.SERVICE.getName());
                            return strategy;
                        }
                        
                        return null;
                    }
                //}
            }
        } catch (ClassNotFoundException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }
	
}

