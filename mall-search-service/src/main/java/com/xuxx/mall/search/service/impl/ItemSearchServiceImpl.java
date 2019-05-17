package com.xuxx.mall.search.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightEntry.Highlight;
import org.springframework.data.solr.core.query.result.HighlightPage;

import com.xuxx.mall.pojo.TbItem;
import com.xuxx.mall.search.service.ItemSearchService;

/**
 * 
 * @ClassName: ItemSearchServiceImpl
 *
 * @author xuxx
 * @date 2019-05-17 14:32:39
 * @since  JDK 1.8
 *
 */
@Service(timeout = 5000)
public class ItemSearchServiceImpl implements ItemSearchService {

	@Autowired
	private SolrTemplate solrTemplate;

	@Override
	public Map search(Map searchMap) {
		Map map = new HashMap();

		HighlightQuery query = new SimpleHighlightQuery();
		Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
		query.addCriteria(criteria);

		HighlightOptions highlightOptions = new HighlightOptions();
		highlightOptions.addField("item_title").setSimplePrefix("<em style='color:red'>")
				.setSimplePostfix("</em>");

		query.setHighlightOptions(highlightOptions);

		HighlightPage<TbItem> page = solrTemplate.queryForHighlightPage(query, TbItem.class);

		List<HighlightEntry<TbItem>> entryList = page.getHighlighted();

		for (HighlightEntry<TbItem> entry : entryList) {
			TbItem item = entry.getEntity();
			// 如果设置了多个高亮字段，则会返回多个
			List<Highlight> highlights = entry.getHighlights();

			// 一个域，可能是 muti 的，如果是多值，需要特殊处理
			/*
			 * for (Highlight highlight : highlights) { List<String> snList =
			 * highlight.getSnipplets(); }
			 */
			if (highlights.size() > 0 && highlights.get(0).getSnipplets().size() > 0) {
				item.setTitle(highlights.get(0).getSnipplets().get(0));
			}
		}

		map.put("rows", page.getContent());

		return map;
	}

}
