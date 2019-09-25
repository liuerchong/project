package com.liu.dao.mapper.business;

import com.liu.model.po.business.PurBusOrderAudit;

public interface PurBusOrderAuditMapper {
    int deleteByPrimaryKey(String id);

    int insert(PurBusOrderAudit record);

    int insertSelective(PurBusOrderAudit record);

    PurBusOrderAudit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PurBusOrderAudit record);

    int updateByPrimaryKey(PurBusOrderAudit record);
}