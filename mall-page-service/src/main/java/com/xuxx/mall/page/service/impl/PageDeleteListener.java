package com.xuxx.mall.page.service.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xuxx.mall.page.service.ItemPageService;

/**
 * 
 * @ClassName: PageDeleteListener
 * <p> 监听删除商品 item 消息，删除对应静态页面</p>
 * @author xuxx
 * @date 2019-05-20 18:17:18
 * @since  JDK 1.8
 *
 */

@Component
public class PageDeleteListener implements MessageListener {

	@Autowired
	private ItemPageService itemPageService;
	
	@Override
	public void onMessage(Message message) {
		
		ObjectMessage objectMessage  =(ObjectMessage)message;
		try {
			Long [] goodsIds= (Long[]) objectMessage.getObject();
			System.out.println("接收到消息:"+goodsIds);
			boolean b = itemPageService.deleteItemHtml(goodsIds);
			System.out.println("删除网页："+b);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
