package com.liu.common.aspact;


import static org.hamcrest.CoreMatchers.nullValue;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.liu.common.annotation.ServiceLog;
import com.liu.common.annotation.SystemControllerLog;
import com.liu.common.annotation.SystemServiceLog;
import com.liu.common.enums.AnnotationTypeEnum;
import com.liu.common.enums.BackupTypeEnum;
import com.liu.common.utils.GenerateId;
import com.liu.common.utils.JsonUtil;
import com.liu.common.utils.ParameterBaseUtils;
import com.liu.common.utils.ThreadUtil;
import com.liu.dao.mapper.log.GeLogMapper;
import com.liu.model.log.SystemLogStrategy;
import com.liu.model.po.log.GeLog;

/**@Comments ：
 * @Author ：刘二冲
 * @Group : K组
 * @Worker: 1852
 * @Date ：2019年9月25日 下午3:09:06
 * @Project ：liuRCProject 
 * @Company ：Vstsoft
 */
@Aspect
//@Component
public class ServiceLogAspect {

	private ConcurrentLinkedQueue logQuene = new ConcurrentLinkedQueue();
	
	
	
	public ConcurrentLinkedQueue getLogQuene() {
		return logQuene;
	}

	public void setLogQuene(ConcurrentLinkedQueue logQuene) {
		this.logQuene = logQuene;
	}

	@Autowired
	private GeLogMapper geLogMapper;
	
	public ServiceLogAspect(){
		System.out.println(">>>>>>>>>>>>>>>>>>>>ServiceLogAspect实例化");
	}
	//private static final Logger LOG = LoggerFactory.getLogger(SystemLogAspect.class);

    private static final Logger LOG = LoggerFactory.getLogger(ServiceLogAspect.class);
    //execution(* com.liu.controller..*(..))
    @Pointcut("execution(* com.liu.service..*(..))&& !execution(* com.liu..*.log..*(..))")
    public void servicePointcut() {
    	System.out.println("服务层切点");

    }
   /* @Pointcut("execution(* com.liu.controller..*(..))")
    public void controllerPointcut() {
    	System.out.println("控制层切点");
    }*/
    
    /** 
	 * 前置通知 用于拦截Controller层记录用户的操作 
	 * 
	 * @param joinPoint 切点 
	 */ 
	@Before("servicePointcut()")
	/*public void doBefore(JoinPoint joinPoint) { //传入连接点对象
		//joinPoint.getTarget().getClass().toString();
	   // System.out.println("==========执行service前置通知==============="+
	    	//	joinPoint.getTarget().getClass().toString());
	   // if(LOG.isInfoEnabled()){
	   // 	LOG.info("before " + joinPoint);
	  //  }
	}  */  

	//配置controller环绕通知,使用在方法aspect()上注册的切入点
	  @Around("servicePointcut()")
	  public Object around(JoinPoint joinPoint){
	      //System.out.println("==========开始执行service环绕通知==============="+joinPoint.getTarget().getClass().toString());
	      long start = System.currentTimeMillis();
	      /*try {  
		        
		       
		        
		        SystemLog log = new SystemLog();  
		        log.setId(UUID.randomUUID().toString());
		        log.setDescription(operationName);  
		        log.setMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()")+"."+operationType);  
		        log.setLogType((long)0);  
		        log.setRequestIp(ip);  
		        log.setExceptioncode( null);  
		        log.setExceptionDetail( null);  
		        log.setParams( null);  
		        log.setCreateBy(user.getName());  
		        log.setCreateDate(new Date());  
		        //保存数据库  
		        systemLogService.insert(log);  
		        System.out.println("=====controller后置通知结束=====");  
		    }  catch (Exception e) {  
		        //记录本地异常日志  
		        logger.error("==后置通知异常==");  
		        logger.error("异常信息:{}", e.getMessage());  
		    }  */
	      try {
	         Object object =  ((ProceedingJoinPoint) joinPoint).proceed();
	          long end = System.currentTimeMillis();
	          Long usetime = end-start;
	          if(LOG.isInfoEnabled()){
	        	  LOG.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
	          }
	          String targetName = joinPoint.getTarget().getClass().getName();  
		        String methodName = joinPoint.getSignature().getName();  
		        Object[] arguments = joinPoint.getArgs();  
		        Class targetClass = Class.forName(targetName);  //通过使用反射机制和连接点对象查找目标方法获取自定义注解的信息
		        Method[] methods = targetClass.getMethods();
		        //String operationType = "";
		       // String operationName = "";
			     String actionDesc = "";
	           	 String actionGroup = "";
	           	 String actionType = "";
	           	 BackupTypeEnum backupType = BackupTypeEnum.BACKUP_DATA;
	           	 boolean insertDb = false;
	           	 String params = ""; 
	           	if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) { 
		              
		              // params=Arrays.toString(joinPoint.getArgs());
	           		for (Object obj : arguments) {
						params += ParameterBaseUtils.gainConditionFromObjectByField(obj);
					}
		             } 
		         for (Method method : methods) {  
		             if (method.getName().equals(methodName)) {  
		                Class[] clazzs = method.getParameterTypes();  
		                 if (clazzs.length == arguments.length) {  
		                	 
		                	 ServiceLog annotation = method.getAnnotation(ServiceLog.class);
		                	 if (null!=annotation) {
								
		                		 actionDesc = annotation.actionDesc();
		                		 actionGroup = annotation.actionGroup();
		                		 actionType = annotation.actionType();
		                		 backupType = annotation.backupType();
		                		 insertDb = annotation.insertDb();
		                		 break;  
							}
		                }  
		            }  
		        }
		         if (insertDb) {
		        	 
					GeLog geLog = new GeLog();
					geLog.setLogId(GenerateId.generate());
					geLog.setOptDesc(actionDesc);
					geLog.setOptMethod(targetName+"."+methodName);
					geLog.setOptParems(params);
					geLog.setBfBz("0");
					geLog.setOptTime(usetime);
					logQuene.add(geLog);
					//geLogMapper.insertSelective(geLog);
					
					invokeLog();
					
				}
		        //*========控制台输出=========*//  
		        System.out.println("=====controller后置通知开始=====");  
		        //*========数据库日志=========*//
	         // System.out.println("==========结束执行servcie环绕通知===============");
		        return object;
	      } catch (Throwable e) {
	    	  System.err.println(e.getMessage());
	          long end = System.currentTimeMillis();
	          if(LOG.isInfoEnabled()){
	        	  LOG.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
	          }
	          LOG.error("==后置通知异常==");  
		      LOG.error("异常信息:{}", e.getMessage()); 
		      return null;
	      }
	  }
    //@Around("pointcut()")
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
	
    //开启多线程处理日志
    public void invokeLog(){
    	
    	LOG.info("处理日志开始");
    	ExecutorService executorService = Executors.newFixedThreadPool(10);
    	executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				
				GeLog geLog = (GeLog)logQuene.poll();
				if (null!=geLog) {
					
					geLogMapper.insertSelective(geLog);
				}
			}
		});
    }
}

