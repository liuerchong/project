package com.liu.service.log.impl;

import org.springframework.stereotype.Service;

import com.liu.model.log.SystemLog;
import com.liu.service.log.SystemLogService;

/**@Comments ：
 * @Author ：刘二冲
 * @Group : K组
 * @Worker: 1852
 * @Date ：2019年9月25日 下午1:53:22
 * @Project ：liuRCProject 
 * @Company ：Vstsoft
 */
@Service("systemLogService")
public class SystemLogServiceImpl implements SystemLogService{

	public void insert(SystemLog log) {
		
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<");
		
	}

}

