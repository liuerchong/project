package com.liu.dao.mapper.log;

import com.liu.model.po.log.GeLog;

public interface GeLogMapper {
    int deleteByPrimaryKey(String logId);

    int insert(GeLog record);

    int insertSelective(GeLog record);

    GeLog selectByPrimaryKey(String logId);

    int updateByPrimaryKeySelective(GeLog record);

    int updateByPrimaryKey(GeLog record);
}