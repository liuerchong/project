package com.liu.common.enums;
/**@Comments ：
 * @Author ：刘二冲
 * @Group : K组
 * @Worker: 1852
 * @Date ：2019年9月25日 下午2:15:35
 * @Project ：liuRCProject 
 * @Company ：Vstsoft
 */
public enum BackupTypeEnum {

	
	COMMON_BACKUP("1","通用操作记录保存,只保存入参和返回值"),
    BACKUP_DATA("2","先将原有数据记录备份后再更新数据"); //这个一般是对于更新操作


    private String code;
    private String msg;
    private BackupTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public String getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}

