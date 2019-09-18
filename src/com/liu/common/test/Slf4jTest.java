package com.liu.common.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**@Comments ：
 * @Author ：刘二冲
 * @Group : K组
 * @Worker: 1852
 * @Date ：2019年9月18日 上午9:57:36
 * @Project ：liuRCProject 
 * @Company ：Vstsoft
 */
public class Slf4jTest {
	
	
	private static Logger logger = LoggerFactory.getLogger(Slf4jTest.class);// slf4j日志记录器

    public static void main(String[] args) {

        // 普通的日志记录
        logger.debug("普通的日志记录");
        System.out.println("111111111111");

        // {}占位符记录日志
        for (int i = 0; i < 3; i++) {
            logger.debug("这是第{}条记录", i);
        }

        // 用\转义{}
        logger.debug("Set \\{} differs from {}", "3"); // output:Set {} differs
                                                        // from 3

        // 两个参数
        logger.debug("两个占位符，可以传两个参数{}----{}", 1, 2);

        // 多个参数(可变参数)
       logger.debug("debug:多个占位符，{},{},{},{}", 1, 2, 3, 4);

        // 多个参数(可变参数)
        logger.info("info:多个占位符，{},{},{},{}", 1, 2, 3, 4);

        // 多个参数(可变参数)
       logger.error("error:多个占位符，{},{},{},{}", 1, 2, 3, 4);
       
       openFile("kkkk");

    }
    
    public static void openFile(String filePath) {
        File file = new File(filePath);
        try {
            InputStream in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
        //下面这两种效果一样
        	logger.error("can found file [{}]", filePath, e);
        	logger.error("can found file [{}],cause:{}", filePath, e.toString()); 

        }
    }


}

