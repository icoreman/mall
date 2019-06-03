package com.xuxx.mall.util.solr;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrDataQuery;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.xuxx.mall.mapper.TbItemMapper;
import com.xuxx.mall.pojo.TbItem;
import com.xuxx.mall.pojo.TbItemExample;
import com.xuxx.mall.pojo.TbItemExample.Criteria;

/**
 * @ClassName: SolrUtil
 *
 * @author xuxx
 * @date 2019-05-17 08:55:11
 * @since JDK 1.8
 *
 */
@Component
public class SolrUtil {	
	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private SolrTemplate solrTemplate;

	@SuppressWarnings("unchecked")
	public void importItemData() {

		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo("1");// 审核通过的才导入的
		List<TbItem> itemList = itemMapper.selectByExample(example);

		System.out.println("---商品列表---");
		for (TbItem item : itemList) {
			//System.out.println(item.getId() + " " + item.getTitle() + " " + item.getPrice());
			@SuppressWarnings("rawtypes")
			Map specMap = JSON.parseObject(item.getSpec(), Map.class);// 从数据库中提取规格json字符串转换为map
			
			item.setSpecMap(specMap);
		}

		solrTemplate.saveBeans(itemList);
		solrTemplate.commit();

		System.out.println("---结束---");
	}

	public void deleteAll() {
		SolrDataQuery query = new SimpleQuery("*:*");
		solrTemplate.delete(query);
		solrTemplate.commit();
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
		SolrUtil solrUtil = (SolrUtil) context.getBean("solrUtil");
		solrUtil.importItemData();
		//solrUtil.deleteAll();

	}
}
