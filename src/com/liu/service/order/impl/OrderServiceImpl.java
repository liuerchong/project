package com.liu.service.order.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liu.common.annotation.ServiceLog;
import com.liu.common.utils.GenerateId;
import com.liu.dao.mapper.business.PurBusOrderMapper;
import com.liu.model.po.business.PurBusOrder;
import com.liu.service.order.OrderService;

/**@Comments ：
 * @Author ：刘二冲
 * @Group : K组
 * @Worker: 1852
 * @Date ：2019年9月25日 上午10:06:11
 * @Project ：liuRCProject 
 * @Company ：Vstsoft
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService{

	@Autowired
	private PurBusOrderMapper purBusOrderMapper ;
	
	@ServiceLog(actionType="create",actionDesc="保存訂單",
			actionGroup="/purchasingflow/flow",insertDb=true)
	public void insertOrder(PurBusOrder purBusOrder) {
		purBusOrder.setId(GenerateId.generate());
		purBusOrderMapper.insert(purBusOrder);
		
	}

}

