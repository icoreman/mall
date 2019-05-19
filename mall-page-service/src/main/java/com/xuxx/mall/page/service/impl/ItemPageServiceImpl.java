package com.xuxx.mall.page.service.impl;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.xuxx.mall.mapper.TbGoodsDescMapper;
import com.xuxx.mall.mapper.TbGoodsMapper;
import com.xuxx.mall.mapper.TbItemCatMapper;
import com.xuxx.mall.mapper.TbItemMapper;
import com.xuxx.mall.page.service.ItemPageService;
import com.xuxx.mall.pojo.TbGoods;
import com.xuxx.mall.pojo.TbGoodsDesc;
import com.xuxx.mall.pojo.TbItem;
import com.xuxx.mall.pojo.TbItemExample;
import com.xuxx.mall.pojo.TbItemExample.Criteria;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * @ClassName: ItemPageServiceImpl
 *
 * @author xuxx
 * @date 2019-05-19 22:28:51
 * @since  JDK 1.8
 *
 */
@Transactional
@Service(timeout = 60000)
public class ItemPageServiceImpl implements ItemPageService {

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Value("${pagedir}")
	private String pagedir;

	@Autowired
	private TbGoodsMapper goodsMapper;

	@Autowired
	private TbGoodsDescMapper goodsDescMapper;

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Autowired
	private TbItemMapper itemMapper;

	@Override
	public boolean genItemHtml(Long goodsId) {
		Configuration configuration = freeMarkerConfigurer.getConfiguration();

		try {
			Template template = configuration.getTemplate("item.ftl");
			// 创建数据模型
			Map<String, Object> dataModel = new HashMap<String, Object>();
			// 1.商品主表数据
			TbGoods goods = goodsMapper.selectByPrimaryKey(goodsId);
			dataModel.put("goods", goods);
			// 2.商品扩展表数据
			TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
			dataModel.put("goodsDesc", goodsDesc);
			
			// 3.读取商品分类
			String itemCat1 = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
			String itemCat2 = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
			String itemCat3 = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
			dataModel.put("itemCat1", itemCat1);
			dataModel.put("itemCat2", itemCat2);
			dataModel.put("itemCat3", itemCat3);

			// 4.读取SKU列表
			TbItemExample example = new TbItemExample();
			Criteria criteria = example.createCriteria();
			criteria.andGoodsIdEqualTo(goodsId);// SPU ID
			criteria.andStatusEqualTo("1");// 状态有效
			example.setOrderByClause("is_default desc");// 按是否默认字段进行降序排序，目的是返回的结果第一条为默认SKU

			List<TbItem> itemList = itemMapper.selectByExample(example);
			dataModel.put("itemList", itemList);

			Writer out = new FileWriter(pagedir + goodsId + ".html");

			template.process(dataModel, out);// 输出
			out.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
