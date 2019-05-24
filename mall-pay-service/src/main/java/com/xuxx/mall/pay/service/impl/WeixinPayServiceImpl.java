package com.xuxx.mall.pay.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.wxpay.sdk.WXPayUtil;
import com.xuxx.mall.pay.service.WeixinPayService;

import util.IdWorker;

/**
 * 
 * @ClassName: WeixinPayServiceImpl 由于没有申请微信支付，所以直接返回结果
 * @author xuxx
 * @date 2019-05-24 10:07:42
 * @since JDK 1.8
 *
 */

@Service
public class WeixinPayServiceImpl implements WeixinPayService {

	@Autowired
	private IdWorker idWorker;

	@Override
	public Map<String, String> createNative(String out_trade_no, String total_fee) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("code_url", "");// 生成支付二维码的链接
		map.put("out_trade_no", out_trade_no);
		map.put("total_fee", total_fee);

		return map;
	}

	@Override
	public Map<String, String> queryPayStatus(String out_trade_no) {
		String xmlResult = "<xml>" +
				"   <return_code><![CDATA[SUCCESS]]></return_code>" +
				"   <return_msg><![CDATA[OK]]></return_msg>" +
				"   <appid><![CDATA[wx2421b1c4370ec43b]]></appid>" +
				"   <mch_id><![CDATA[10000100]]></mch_id>" +
				"   <device_info><![CDATA[1000]]></device_info>" +
				"   <nonce_str><![CDATA[TN55wO9Pba5yENl8]]></nonce_str>" +
				"   <sign><![CDATA[BDF0099C15FF7BC6B1585FBB110AB635]]></sign>" +
				"   <result_code><![CDATA[SUCCESS]]></result_code>" +
				"   <openid><![CDATA[oUpF8uN95-Ptaags6E_roPHg7AG0]]></openid>" +
				"   <is_subscribe><![CDATA[Y]]></is_subscribe>" +
				"   <trade_type><![CDATA[MICROPAY]]></trade_type>" +
				"   <bank_type><![CDATA[CCB_DEBIT]]></bank_type>" +
				"   <total_fee>1</total_fee>" +
				"   <fee_type><![CDATA[CNY]]></fee_type>" +
				"   <transaction_id>" + idWorker.nextId() + "</transaction_id>" +
				"   <out_trade_no>" + out_trade_no + "</out_trade_no>" +
				"   <attach><![CDATA[订单额外描述]]></attach>" +
				"   <time_end><![CDATA[20141111170043]]></time_end>" +
				"   <trade_state><![CDATA[SUCCESS]]></trade_state>" +
				"</xml>";
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = WXPayUtil.xmlToMap(xmlResult);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return map;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public Map<String, String> closePay(String out_trade_no) {
		String xmlResult = "<xml>" +
				"   <return_code><![CDATA[SUCCESS]]></return_code>" +
				"   <return_msg><![CDATA[OK]]></return_msg>" +
				"   <appid><![CDATA[wx2421b1c4370ec43b]]></appid>" +
				"   <mch_id><![CDATA[10000100]]></mch_id>" +
				"   <nonce_str><![CDATA[BFK89FC6rxKCOjLX]]></nonce_str>" +
				"   <sign><![CDATA[72B321D92A7BFA0B2509F3D13C7B1631]]></sign>" +
				"   <result_code><![CDATA[SUCCESS]]></result_code>" +
				"   <result_msg><![CDATA[OK]]></result_msg>" +
				"</xml>";
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = WXPayUtil.xmlToMap(xmlResult);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return map;
		}
	}
}
