package com.xuxx.mall.manager.controller;

import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.xuxx.entity.PageResult;
import com.xuxx.entity.Result;
import com.xuxx.mall.pojo.TbGoods;
import com.xuxx.mall.pojo.TbItem;
import com.xuxx.mall.sellergoods.service.GoodsService;
import com.xuxx.mall.vo.GoodsVO;

/**
 * controller
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private Destination queueSolrAddItemDestination; // 用于导入 item solr索引库的消息目标（点对点）

	@Autowired
	private Destination queueSolrDeleteItemDestination;// 用于删除 item solr 索引库（点对点）

	@Autowired
	private Destination topicCreateItemPageDestination;// 用于生成商品详细页的消息目标(发布订阅)

	@Autowired
	private Destination topicDeleteItemPageDestination;// 用于删除商品详细页的消息目标(发布订阅)

	/**
	 * 返回全部列表
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll() {
		return goodsService.findAll();
	}

	/**
	 * 返回全部列表
	 * 
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult<TbGoods> findPage(int page, int rows) {
		return goodsService.findPage(page, rows);
	}

	/**
	 * 修改
	 * 
	 * @param goods
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody GoodsVO goods) {
		try {
			goodsService.update(goods);
			return Result.buildSuccessResult("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.buildFailResult("修改失败");
		}
	}

	/**
	 * 获取实体
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public GoodsVO findOne(Long id) {
		return goodsService.findOne(id);
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long[] ids) {
		try {
			goodsService.delete(ids);

			// 从索引库中删除
			jmsTemplate.send(queueSolrDeleteItemDestination, new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					return session.createObjectMessage(ids);
				}
			});

			// 删除每个服务器上的商品详细页
			jmsTemplate.send(topicDeleteItemPageDestination, new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					return session.createObjectMessage(ids);
				}
			});

			return Result.buildSuccessResult("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.buildFailResult("删除失败");
		}
	}

	/**
	 * 查询+分页
	 * 
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult<TbGoods> search(@RequestBody TbGoods goods, int page, int rows) {
		return goodsService.findPage(goods, page, rows);
	}

	@RequestMapping("/updateStatus")
	public Result updateStatus(Long[] ids, String status) {
		try {
			goodsService.updateStatus(ids, status);

			if ("1".equals(status)) {// 如果是审核通过
				// 得到需要导入的SKU列表
				List<TbItem> itemList = goodsService.findItemListByGoodsIdListAndStatus(ids, status);

				// 导入到solr,转换为json传输
				final String jsonString = JSON.toJSONString(itemList);

				jmsTemplate.send(queueSolrAddItemDestination, new MessageCreator() {
					@Override
					public Message createMessage(Session session) throws JMSException {

						return session.createTextMessage(jsonString);
					}
				});

				// 生成商品详情页
				for (final Long goodsId : ids) {
					jmsTemplate.send(topicCreateItemPageDestination, new MessageCreator() {
						@Override
						public Message createMessage(Session session) throws JMSException {
							return session.createTextMessage(goodsId + "");
						}
					});
				}
			}

			return Result.buildSuccessResult("成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.buildFailResult("失败");
		}
	}

	@RequestMapping("/genHtml")
	public void genHtml(Long goodsId) {
		jmsTemplate.send(topicCreateItemPageDestination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(goodsId + "");
			}
		});
	}
}
