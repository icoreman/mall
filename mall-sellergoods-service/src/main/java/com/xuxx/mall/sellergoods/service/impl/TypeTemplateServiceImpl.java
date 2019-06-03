package com.xuxx.mall.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuxx.entity.PageResult;
import com.xuxx.mall.mapper.TbSpecificationOptionMapper;
import com.xuxx.mall.mapper.TbTypeTemplateMapper;
import com.xuxx.mall.pojo.TbSpecificationOption;
import com.xuxx.mall.pojo.TbSpecificationOptionExample;
import com.xuxx.mall.pojo.TbTypeTemplate;
import com.xuxx.mall.pojo.TbTypeTemplateExample;
import com.xuxx.mall.pojo.TbTypeTemplateExample.Criteria;
import com.xuxx.mall.sellergoods.service.TypeTemplateService;

/**
 * 
 * @ClassName: TypeTemplateServiceImpl
 *
 * @author xuxx
 * @date 2019-05-13 17:27:08
 * @since JDK 1.8
 *
 */
@SuppressWarnings(value = { "unchecked", "rawtypes" })
@Transactional
@Service(interfaceClass = TypeTemplateService.class, timeout = 10000)
public class TypeTemplateServiceImpl implements TypeTemplateService {
	private static final Logger log = Logger.getLogger(TypeTemplateServiceImpl.class);

	@Autowired
	private TbTypeTemplateMapper typeTemplateMapper;

	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 查询全部
	 */
	@Override
	public List<TbTypeTemplate> findAll() {
		return typeTemplateMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult<TbTypeTemplate> findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbTypeTemplate> page = (Page<TbTypeTemplate>) typeTemplateMapper.selectByExample(null);
		return new PageResult<TbTypeTemplate>(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbTypeTemplate typeTemplate) {
		typeTemplateMapper.insert(typeTemplate);
	}

	/**
	 * 修改
	 */
	@Override
	public void update(TbTypeTemplate typeTemplate) {
		typeTemplateMapper.updateByPrimaryKey(typeTemplate);
	}

	/**
	 * 根据ID获取实体
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public TbTypeTemplate findOne(Long id) {
		return typeTemplateMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			typeTemplateMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public PageResult<TbTypeTemplate> findPage(TbTypeTemplate typeTemplate, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		TbTypeTemplateExample example = new TbTypeTemplateExample();
		Criteria criteria = example.createCriteria();

		if (typeTemplate != null) {
			if (typeTemplate.getName() != null && typeTemplate.getName().length() > 0) {
				criteria.andNameLike("%" + typeTemplate.getName() + "%");
			}
			if (typeTemplate.getSpecIds() != null && typeTemplate.getSpecIds().length() > 0) {
				criteria.andSpecIdsLike("%" + typeTemplate.getSpecIds() + "%");
			}
			if (typeTemplate.getBrandIds() != null && typeTemplate.getBrandIds().length() > 0) {
				criteria.andBrandIdsLike("%" + typeTemplate.getBrandIds() + "%");
			}
			if (typeTemplate.getCustomAttributeItems() != null && typeTemplate.getCustomAttributeItems().length() > 0) {
				criteria.andCustomAttributeItemsLike("%" + typeTemplate.getCustomAttributeItems() + "%");
			}

		}

		Page<TbTypeTemplate> page = (Page<TbTypeTemplate>) typeTemplateMapper.selectByExample(example);

		// 缓存 品牌列表、规格列表到 redis
		// 因为 增删改之后，都要重新查询列表，所以直接写在这
		saveToRedis();
		return new PageResult<TbTypeTemplate>(page.getTotal(), page.getResult());
	}

	/**
	 * 
	 * @Title: saveToRedis
	 * @Description: 将品牌列表与规格列表放入缓存, 以模板 id 为key
	 */
	private void saveToRedis() {
		log.info("缓存品牌列表和规格列表");
		List<TbTypeTemplate> templateList = findAll();
		for (TbTypeTemplate template : templateList) {
			// 得到品牌列表
			List<Map> brandList = JSON.parseArray(template.getBrandIds(), Map.class);
			redisTemplate.boundHashOps("brandList").put(template.getId(), brandList);

			// 得到规格列表
			List<Map> specList = findSpecList(template.getId());
			redisTemplate.boundHashOps("specList").put(template.getId(), specList);
		}
	}

	@Override
	public List<Map<String, String>> selectOptionList() {
		return typeTemplateMapper.selectOptionList();
	}

	@Override
	public List<Map> findSpecList(Long id) {
		// 查询模板
		TbTypeTemplate typeTemplate = typeTemplateMapper.selectByPrimaryKey(id);

		List<Map> list = JSON.parseArray(typeTemplate.getSpecIds(), Map.class);

		for (Map map : list) {
			// 查询规格选项列表
			TbSpecificationOptionExample example = new TbSpecificationOptionExample();
			TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
			criteria.andSpecIdEqualTo(new Long((Integer) map.get("id")));

			List<TbSpecificationOption> options = specificationOptionMapper.selectByExample(example);

			map.put("options", options);
		}

		return list;
	}
}
