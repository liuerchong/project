package com.liu.model.po.log;

import java.util.Date;

public class GeLog {
    private String logId;

    private String userId;

    private String userName;

    private String account;

    private Date loginDate;

    private Date exitDate;

    private String optStatus;

    private String optMethod;

    private String optError;

    private String optParems;

    private String optDesc;

    private String reqIp;

    private String exceptionCode;

    private String optType;

    private String optSql;

    private String bfBz;

    private long optTime;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId == null ? null : logId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getExitDate() {
        return exitDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }

    public String getOptStatus() {
        return optStatus;
    }

    public void setOptStatus(String optStatus) {
        this.optStatus = optStatus == null ? null : optStatus.trim();
    }

    public String getOptMethod() {
        return optMethod;
    }

    public void setOptMethod(String optMethod) {
        this.optMethod = optMethod == null ? null : optMethod.trim();
    }

    public String getOptError() {
        return optError;
    }

    public void setOptError(String optError) {
        this.optError = optError == null ? null : optError.trim();
    }

    public String getOptParems() {
        return optParems;
    }

    public void setOptParems(String optParems) {
        this.optParems = optParems == null ? null : optParems.trim();
    }

    public String getOptDesc() {
        return optDesc;
    }

    public void setOptDesc(String optDesc) {
        this.optDesc = optDesc == null ? null : optDesc.trim();
    }

    public String getReqIp() {
        return reqIp;
    }

    public void setReqIp(String reqIp) {
        this.reqIp = reqIp == null ? null : reqIp.trim();
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode == null ? null : exceptionCode.trim();
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType == null ? null : optType.trim();
    }

    public String getOptSql() {
        return optSql;
    }

    public void setOptSql(String optSql) {
        this.optSql = optSql == null ? null : optSql.trim();
    }

    public String getBfBz() {
        return bfBz;
    }

    public void setBfBz(String bfBz) {
        this.bfBz = bfBz == null ? null : bfBz.trim();
    }

    public long getOptTime() {
        return optTime;
    }

    public void setOptTime(long optTime) {
        this.optTime = optTime;
    }
}