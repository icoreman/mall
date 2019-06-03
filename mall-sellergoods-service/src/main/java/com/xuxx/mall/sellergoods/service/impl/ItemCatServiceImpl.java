package com.xuxx.mall.sellergoods.service.impl;

import java.util.List;

import org.apache.dubbo.config.annotation.Service;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuxx.entity.PageResult;
import com.xuxx.mall.mapper.TbItemCatMapper;
import com.xuxx.mall.pojo.TbItemCat;
import com.xuxx.mall.pojo.TbItemCatExample;
import com.xuxx.mall.pojo.TbItemCatExample.Criteria;
import com.xuxx.mall.sellergoods.service.ItemCatService;

/**
 * 
 * @ClassName: ItemCatServiceImpl
 *
 * @author xuxx
 * @date 2019-05-13 17:18:51
 * @since JDK 1.8
 *
 */
@Transactional
@Service(interfaceClass = ItemCatService.class, timeout = 10000)
public class ItemCatServiceImpl implements ItemCatService {
	private static final Logger log = Logger.getLogger(ItemCatServiceImpl.class);

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 查询全部
	 */
	@Override
	public List<TbItemCat> findAll() {
		return itemCatMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult<TbItemCat> findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbItemCat> page = (Page<TbItemCat>) itemCatMapper.selectByExample(null);
		return new PageResult<TbItemCat>(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbItemCat itemCat) {
		itemCatMapper.insert(itemCat);
	}

	/**
	 * 修改
	 */
	@Override
	public void update(TbItemCat itemCat) {
		itemCatMapper.updateByPrimaryKey(itemCat);
	}

	/**
	 * 根据ID获取实体
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public TbItemCat findOne(Long id) {
		return itemCatMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			itemCatMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public PageResult<TbItemCat> findPage(TbItemCat itemCat, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();

		if (itemCat != null) {
			if (itemCat.getName() != null && itemCat.getName().length() > 0) {
				criteria.andNameLike("%" + itemCat.getName() + "%");
			}
		}

		Page<TbItemCat> page = (Page<TbItemCat>) itemCatMapper.selectByExample(example);
		return new PageResult<TbItemCat>(page.getTotal(), page.getResult());
	}

	@Override
	public List<TbItemCat> findByParentId(Long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);

		// 将模板ID放入缓存（以商品分类名称作为key）
		log.info("缓存商品分类");
		List<TbItemCat> itemCatList = findAll();
		for (TbItemCat itemCat : itemCatList) {
			redisTemplate.boundHashOps("itemCat").put(itemCat.getName(), itemCat.getTypeId());
		}

		return itemCatMapper.selectByExample(example);
	}
}
