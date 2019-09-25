package com.liu.common.enums;

import org.opensaml.ws.wstrust.Primary;

/**@Comments ：
 * @Author ：刘二冲
 * @Group : K组
 * @Worker: 1852
 * @Date ：2019年9月25日 下午3:45:18
 * @Project ：liuRCProject 
 * @Company ：Vstsoft
 */
public enum AnnotationTypeEnum {

	
	CONTROLLER("controller"),
	SERVICE("service");
	
	private String name;

	private AnnotationTypeEnum(String name) {
        this.name = name;
    }
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

