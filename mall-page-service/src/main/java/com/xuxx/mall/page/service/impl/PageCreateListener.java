package com.xuxx.mall.page.service.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xuxx.mall.page.service.ItemPageService;

/**
 * 
 * @ClassName: PageCreateListener
 * <p>监听生成网页的消息主题，生成对应静态页面</p>
 * @author xuxx
 * @date 2019-05-20 18:15:20
 * @since  JDK 1.8
 *
 */
@Component
public class PageCreateListener implements MessageListener {
	private static final Logger log = Logger.getLogger(PageCreateListener.class);
	
	@Autowired
	private ItemPageService itemPageService;

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			String text = textMessage.getText();
			log.info("接收到消息：" + text);
			boolean b = itemPageService.genItemHtml(Long.parseLong(text));
			log.info("网页生成结果：" + b);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}