package com.xuxx.mall.search.service.impl;

import java.util.Arrays;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;
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
	private static final Logger log = Logger.getLogger(ItemDeleteListener.class);
	
	@Autowired
	private ItemSearchService itemSearchService;

	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage = (ObjectMessage) message;
		try {
			Long[] goodsIds = (Long[]) objectMessage.getObject();
			log.info("监听获取到消息：" + goodsIds);
			itemSearchService.deleteByGoodsIds(Arrays.asList(goodsIds));
			log.info("执行索引库删除");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
