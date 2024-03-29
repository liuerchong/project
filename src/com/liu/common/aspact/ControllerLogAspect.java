package com.liu.common.aspact;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.liu.common.annotation.Log;
import com.liu.common.annotation.User;
import com.liu.common.utils.JsonUtil;
import com.liu.model.log.SystemLog;
import com.liu.service.log.SystemLogService;

/**@Comments ：
 * @Author ：刘二冲
 * @Group : K组
 * @Worker: 1852
 * @Date ：2019年9月18日 上午10:24:54
 * @Project ：liuRCProject 
 * @Company ：Vstsoft
 */
//@Aspect 
//标记为一个切面类（需要在配置文件中开启基于注解的切面支持<aop:aspectj-autoProxy proxy-target-class="true">）
@Aspect
@Component  //标记该类交由spring管理，就不用使用xml文件配置该bean了
public class ControllerLogAspect {
	
	public ControllerLogAspect() {
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<切面初始化");
	}
	//注入Service用于把日志保存数据库  
	@Resource  //这里我用resource注解，一般用的是@Autowired，他们的区别如有时间我会在后面的博客中来写
	private SystemLogService systemLogService;  
	
	private  static  final Logger logger = LoggerFactory.getLogger(ControllerLogAspect. class);   //slf4j 抽象日志变量
	
	//Controller层切点  
	//@Pointcut("execution(* com.liu.controller..*(..))")   
	//@Pointcut("@annotation(com.liu.common.annotation.Log)")   
	//切入点表达式这样写在后边的通知方法中就可以使用该方法不用反复的写该切入点表达式了。
	@Pointcut("execution(* com.liu.controller..*(..))&& !execution(* com.liu..*.log..*(..))")
	public  void controllerAspect() {  
		System.out.println("《《《《《《《《《《《《《《《《切入点");
		
	}  
	
	/** 
	 * 前置通知 用于拦截Controller层记录用户的操作 
	 * 
	 * @param joinPoint 切点 
	 */ 
	//@Before("controllerAspect()")
	/*public void doBefore(JoinPoint joinPoint) { //传入连接点对象
	    System.out.println("==========执行controller前置通知===============");
	    if(logger.isInfoEnabled()){
	        logger.info("before " + joinPoint);
	    }
	}  */  
	
	//配置controller环绕通知,使用在方法aspect()上注册的切入点
	  @Around("controllerAspect()")
	  public Object around(JoinPoint joinPoint){
	      System.out.println("==========开始执行controller环绕通知===============");
	      long start = System.currentTimeMillis();
	      try {
	          Object object = ((ProceedingJoinPoint) joinPoint).proceed();
	          Object[] arguments = joinPoint.getArgs(); 
	          
	          long end = System.currentTimeMillis();
	          if(logger.isInfoEnabled()){
	              logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
	          }
	          System.out.println("==========结束执行controller环绕通知===============");
	          return object;
	      } catch (Throwable e) {
	          long end = System.currentTimeMillis();
	          if(logger.isInfoEnabled()){
	              logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
	          }
	          return null;
	      }
	  }
	
	/** 
	 * 后置通知 用于拦截Controller层记录用户的操作 
	 * 
	 * @param joinPoint 切点 
	 */  
	//@After("controllerAspect()")  
	public  void after(JoinPoint joinPoint) {  
	
	   /* HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();  
	    HttpSession session = request.getSession();  */
	    //读取session中的用户  
	   // User user = (User) session.getAttribute("user");  
	    //请求的IP  
	    //String ip = request.getRemoteAddr();
	    User user = new User();
	    user.setIp("44");
	    user.setName("张三");
	    String ip = "127.0.0.1";
	     try {  
	        
	        String targetName = joinPoint.getTarget().getClass().getName();  
	        String methodName = joinPoint.getSignature().getName();  
	        Object[] arguments = joinPoint.getArgs();  
	        Class targetClass = Class.forName(targetName);  //通过使用反射机制和连接点对象查找目标方法获取自定义注解的信息
	        Method[] methods = targetClass.getMethods();
	        String operationType = "";
	        String operationName = "";
	         for (Method method : methods) {  
	             if (method.getName().equals(methodName)) {  
	                Class[] clazzs = method.getParameterTypes();  
	                 if (clazzs.length == arguments.length) {  
	                     operationType = method.getAnnotation(Log.class).operationType();
	                     operationName = method.getAnnotation(Log.class).operationName();
	                     break;  
	                }  
	            }  
	        }
	        //*========控制台输出=========*//  
	        System.out.println("=====controller后置通知开始=====");  
	        System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()")+"."+operationType);  
	        System.out.println("方法描述:" + operationName);  
	        System.out.println("请求人:" + user.getName());  
	        System.out.println("请求IP:" + ip);  
	        //*========数据库日志=========*//
	        
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
	    }  
	} 
	
	//配置后置返回通知,使用在方法aspect()上注册的切入点
	 // @AfterReturning("controllerAspect()")
	  public void afterReturn(JoinPoint joinPoint){
	      System.out.println("=====执行controller后置返回通知=====");  
	          if(logger.isInfoEnabled()){
	              logger.info("afterReturn " + joinPoint);
	          }
	  }
	
	/** 
	 * 异常通知 用于拦截记录异常日志 
	 * 
	 * @param joinPoint 
	 * @param e 
	 */  
	// @AfterThrowing(pointcut = "controllerAspect()", throwing="e")  
	 public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {  
	    /*HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();  
	    HttpSession session = request.getSession();  
	    //读取session中的用户  
	    User user = (User) session.getAttribute(WebConstants.CURRENT_USER);  
	    //获取请求ip  
	    String ip = request.getRemoteAddr(); */ 
	    //获取用户请求方法的参数并序列化为JSON格式字符串  
	    
	    User user = new User();
	    user.setId(1);
	    user.setName("张三");
	    String ip = "127.0.0.1";
	    
	    String params = "";  
	     if (joinPoint.getArgs() !=  null && joinPoint.getArgs().length > 0) {  
	         for ( int i = 0; i < joinPoint.getArgs().length; i++) {  
	            params += JsonUtil.getJsonStr(joinPoint.getArgs()[i]) + ";";  
	        }  
	    }  
	     try {  
	         
	         String targetName = joinPoint.getTarget().getClass().getName();  
	         String methodName = joinPoint.getSignature().getName();  
	         Object[] arguments = joinPoint.getArgs();  
	         Class targetClass = Class.forName(targetName);  
	         Method[] methods = targetClass.getMethods();
	         String operationType = "";
	         String operationName = "";
	          for (Method method : methods) {  
	              if (method.getName().equals(methodName)) {  
	                 Class[] clazzs = method.getParameterTypes();  
	                  if (clazzs.length == arguments.length) {  
	                      operationType = method.getAnnotation(Log.class).operationType();
	                      operationName = method.getAnnotation(Log.class).operationName();
	                      break;  
	                 }  
	             }  
	         }
	         /*========控制台输出=========*/  
	        System.out.println("=====异常通知开始=====");  
	        System.out.println("异常代码:" + e.getClass().getName());  
	        System.out.println("异常信息:" + e.getMessage());  
	        System.out.println("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()")+"."+operationType);  
	        System.out.println("方法描述:" + operationName);  
	        System.out.println("请求人:" + user.getName());  
	        System.out.println("请求IP:" + ip);  
	        System.out.println("请求参数:" + params);  
	           /*==========数据库日志=========*/  
	        SystemLog log = new SystemLog();
	        log.setId(UUID.randomUUID().toString());
	        log.setDescription(operationName);  
	        log.setExceptioncode(e.getClass().getName());  
	        log.setLogType((long)1);  
	        log.setExceptionDetail(e.getMessage());  
	        log.setMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));  
	        log.setParams(params);  
	        log.setCreateBy(user.getName());  
	        log.setCreateDate(new Date());  
	        log.setRequestIp(ip);  
	        //保存数据库  
	        systemLogService.insert(log);  
	        System.out.println("=====异常通知结束=====");  
	    }  catch (Exception ex) {  
	        //记录本地异常日志  
	        logger.error("==异常通知异常==");  
	        logger.error("异常信息:{}", ex.getMessage());  
	    }  
	     /*==========记录本地异常日志==========*/  
	    logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage(), params);  
	
	}  

}

