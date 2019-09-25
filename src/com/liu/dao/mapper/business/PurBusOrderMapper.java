package com.liu.dao.mapper.business;

import com.liu.model.po.business.PurBusOrder;

public interface PurBusOrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(PurBusOrder record);

    int insertSelective(PurBusOrder record);

    PurBusOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PurBusOrder record);

    int updateByPrimaryKey(PurBusOrder record);
}