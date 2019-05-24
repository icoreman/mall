package com.xuxx.mall.pay.service;

import java.util.Map;

/**
 * 
 * @ClassName: WeixinPayService
 *
 * @author xuxx
 * @date 2019-05-24 10:03:07
 * @since JDK 1.8
 *
 */
public interface WeixinPayService {

	/**
	 * 生成二维码
	 * 
	 * @param out_trade_no
	 * @param total_fee
	 * @return
	 */
	public Map<String, String> createNative(String out_trade_no, String total_fee);

	/**
	 * 查询支付订单状态
	 * 直接返回支付成功
	 * @param out_trade_no
	 * @return
	 */
	public Map<String, String> queryPayStatus(String out_trade_no);
	
	/**
	 * 关闭订单
	 * @param out_trade_no
	 * @return
	 */
	public Map<String, String> closePay(String out_trade_no);
}
