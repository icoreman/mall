package com.xuxx.mall.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuxx.entity.PageResult;
import com.xuxx.mall.mapper.TbBrandMapper;
import com.xuxx.mall.pojo.TbBrand;
import com.xuxx.mall.pojo.TbBrandExample;
import com.xuxx.mall.pojo.TbBrandExample.Criteria;
import com.xuxx.mall.sellergoods.service.BrandService;

/**
 * 
 * @ClassName: BrandServiceImpl
 *
 * @author xuxx
 * @date 2019-05-13 14:52:46
 * @since JDK 1.8
 *
 */
@Transactional
@Service(interfaceClass = BrandService.class)
public class BrandServiceImpl implements BrandService {

	@Autowired
	private TbBrandMapper brandMapper;

	@Override
	public List<TbBrand> findAll() {

		return brandMapper.selectByExample(null);
	}

	@Override
	public PageResult<TbBrand> findPage(int pageNum, int pageSize) {

		PageHelper.startPage(pageNum, pageSize);// 分页

		Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(null);

		return new PageResult<TbBrand>(page.getTotal(), page.getResult());
	}

	@Override
	public void add(TbBrand brand) {

		brandMapper.insert(brand);
	}

	@Override
	public TbBrand findOne(Long id) {

		return brandMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(TbBrand brand) {
		brandMapper.updateByPrimaryKey(brand);
	}

	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			brandMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public PageResult<TbBrand> findPage(TbBrand brand, int pageNum, int pageSize) {

		PageHelper.startPage(pageNum, pageSize);// 分页

		TbBrandExample example = new TbBrandExample();

		Criteria criteria = example.createCriteria();
		if (brand != null) {
			if (brand.getName() != null && brand.getName().length() > 0) {
				criteria.andNameLike("%" + brand.getName() + "%");
			}
			if (brand.getFirstChar() != null && brand.getFirstChar().length() > 0) {
				criteria.andFirstCharLike("%" + brand.getFirstChar() + "%");
			}
		}

		Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(example);

		return new PageResult<TbBrand>(page.getTotal(), page.getResult());
	}

	@Override
	public List<Map<String, String>> selectOptionList() {
		return brandMapper.selectOptionList();
	}
}
