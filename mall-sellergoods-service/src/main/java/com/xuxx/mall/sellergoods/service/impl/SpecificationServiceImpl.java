package com.xuxx.mall.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuxx.entity.PageResult;
import com.xuxx.mall.mapper.TbSpecificationMapper;
import com.xuxx.mall.mapper.TbSpecificationOptionMapper;
import com.xuxx.mall.pojo.TbSpecification;
import com.xuxx.mall.pojo.TbSpecificationExample;
import com.xuxx.mall.pojo.TbSpecificationExample.Criteria;
import com.xuxx.mall.pojo.TbSpecificationOption;
import com.xuxx.mall.pojo.TbSpecificationOptionExample;
import com.xuxx.mall.sellergoods.service.SpecificationService;
import com.xuxx.mall.vo.SpecificationVO;

/**
 * 
 * @ClassName: SpecificationServiceImpl
 *
 * @author xuxx
 * @date 2019-05-13 17:26:42
 * @since JDK 1.8
 *
 */
@Transactional
@Service(interfaceClass = SpecificationService.class)
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;

	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;

	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult<TbSpecification> findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbSpecification> page = (Page<TbSpecification>) specificationMapper.selectByExample(null);
		return new PageResult<TbSpecification>(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(SpecificationVO specification) {
		// 获取规格实体
		TbSpecification tbspecification = specification.getSpecification();
		specificationMapper.insert(tbspecification);

		// 获取规格选项集合
		List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
		for (TbSpecificationOption option : specificationOptionList) {
			option.setSpecId(tbspecification.getId());// 设置规格ID
			specificationOptionMapper.insert(option);// 新增规格
		}
	}

	/**
	 * 修改
	 */
	@Override
	public void update(SpecificationVO specification) {

		// 获取规格实体
		TbSpecification tbspecification = specification.getSpecification();
		specificationMapper.updateByPrimaryKey(tbspecification);

		// 删除原来规格对应的规格选项

		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(tbspecification.getId());
		specificationOptionMapper.deleteByExample(example);

		// 获取规格选项集合
		List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
		for (TbSpecificationOption option : specificationOptionList) {
			option.setSpecId(tbspecification.getId());// 设置规格ID
			specificationOptionMapper.insert(option);// 新增规格
		}

	}

	/**
	 * 根据ID获取实体
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public SpecificationVO findOne(Long id) {

		SpecificationVO specification = new SpecificationVO();
		// 获取规格实体
		TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
		specification.setSpecification(tbSpecification);

		// 获取规格选项列表

		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(id);
		List<TbSpecificationOption> specificationOptionList = specificationOptionMapper.selectByExample(example);

		specification.setSpecificationOptionList(specificationOptionList);

		return specification;// 组合实体类
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			// 删除规格表数据
			specificationMapper.deleteByPrimaryKey(id);

			// 删除规格选项表数据
			TbSpecificationOptionExample example = new TbSpecificationOptionExample();
			TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
			criteria.andSpecIdEqualTo(id);
			specificationOptionMapper.deleteByExample(example);
		}
	}

	@Override
	public PageResult<TbSpecification> findPage(TbSpecification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		TbSpecificationExample example = new TbSpecificationExample();
		Criteria criteria = example.createCriteria();

		if (specification != null) {
			if (specification.getSpecName() != null && specification.getSpecName().length() > 0) {
				criteria.andSpecNameLike("%" + specification.getSpecName() + "%");
			}

		}

		Page<TbSpecification> page = (Page<TbSpecification>) specificationMapper.selectByExample(example);
		return new PageResult<TbSpecification>(page.getTotal(), page.getResult());
	}

	@Override
	public List<Map<String, String>> selectOptionList() {
		return specificationMapper.selectOptionList();
	}
}