package com.xuxx.mall.search.service.impl;

import java.util.Arrays;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xuxx.mall.search.service.ItemSearchService;

/**
 * 
 * @ClassName: ItemDeleteListener
 * <p>监听商品删除消息队列，删除对应 solr 索引</p>
 * @author xuxx
 * @date 2019-05-20 18:20:21
 * @since  JDK 1.8
 *
 */
@Component
public class ItemDeleteListener implements MessageListener {

	@Autowired
	private ItemSearchService itemSearchService;

	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage = (ObjectMessage) message;
		try {
			Long[] goodsIds = (Long[]) objectMessage.getObject();
			System.out.println("监听获取到消息：" + goodsIds);
			itemSearchService.deleteByGoodsIds(Arrays.asList(goodsIds));
			System.out.println("执行索引库删除");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
