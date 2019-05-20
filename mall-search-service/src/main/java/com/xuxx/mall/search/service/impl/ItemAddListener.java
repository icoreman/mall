package com.xuxx.mall.search.service.impl;

import java.util.List;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.xuxx.mall.pojo.TbItem;
import com.xuxx.mall.search.service.ItemSearchService;

/**
 * 
 * @ClassName: ItemAddListener
 * <p>监听商品新增消息队列，添加对应 solr 索引</p>
 * @author xuxx
 * @date 2019-05-20 18:19:32
 * @since  JDK 1.8
 *
 */
@Component
public class ItemAddListener implements MessageListener {

	@Autowired
	private ItemSearchService itemSearchService;
	
	@Override
	public void onMessage(Message message) {
		
		TextMessage textMessage=(TextMessage)message;
		try {
			String text = textMessage.getText();//json字符串
			System.out.println("监听到消息:"+text);
			
			List<TbItem> itemList = JSON.parseArray(text, TbItem.class);
			itemSearchService.importList(itemList);
			System.out.println("导入到solr索引库");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
