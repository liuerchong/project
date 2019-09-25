package com.liu.controller.flow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liu.common.annotation.SystemControllerLog;
import com.liu.common.result.ResultInfo;
import com.liu.common.result.SubmitResultInfo;
import com.liu.model.po.business.PurBusOrder;
import com.liu.service.order.OrderService;

/**@Comments ：
 * @Author ：刘二冲
 * @Group : K组
 * @Worker: 1852
 * @Date ：2019年9月25日 上午9:09:17
 * @Project ：liuRCProject 
 * @Company ：Vstsoft
 */
@Controller
@RequestMapping("/purchasingflow/flow")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	@RequestMapping("/orderCreat")
	public String toCreateOrder(){
		return "/order/addOrder";
	}
	
	@RequestMapping("/addOrderSubmit")
	@ResponseBody
	//@Log(operationType="add操作:",operationName="添加用户") 
	@SystemControllerLog(description="保存订单")
	public SubmitResultInfo createOrder(PurBusOrder purBusOrder ){
		
	//提示用户信息
		
//		String message = "操作成功！！";
//		int type=0;//成功
		//默认为成功
		ResultInfo resultInfo = new ResultInfo();
		resultInfo.setType(ResultInfo.TYPE_RESULT_SUCCESS);
		resultInfo.setMessage("操作成功！");
		
		/*try {
			//调用service执行用户添加
			userService.insertSysuser(sysuserQueryVo.getSysuserCustom());
		} catch (Exception e) {
			//输出异常信息
			e.printStackTrace();
			//对应异常信息进行解析
//			message = e.getMessage();
//			type=1;//失败
			//解析系统自定义异常
			if(e instanceof ExceptionResultInfo){
				//抛出的是系统自定义异常
				resultInfo = ((ExceptionResultInfo)e).getResultInfo();
			}else{
				//重新构造“未知错误”异常
				resultInfo = new ResultInfo();
				resultInfo.setType(ResultInfo.TYPE_RESULT_FAIL);
				resultInfo.setMessage("未知错误！");
			}
		}*/
		
		//使用全局异常处理器后，在actoin不用进行try/catch捕获
		orderService.insertOrder(purBusOrder);
		
		//将执行结果返回页面
		
//		Map<String, Object> result_map = new HashMap<String, Object>();
//		result_map.put("type", type);
//		result_map.put("message", message);
		
		SubmitResultInfo submitResultInfo = new SubmitResultInfo(resultInfo);
		
		return submitResultInfo;
	}
	
}

