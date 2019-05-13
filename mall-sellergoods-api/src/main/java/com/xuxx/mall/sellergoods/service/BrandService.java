package com.xuxx.mall.sellergoods.service;

import java.util.List;
import java.util.Map;

import com.xuxx.entity.PageResult;
import com.xuxx.mall.pojo.TbBrand;

/**
 * 品牌接口
 * @ClassName: BrandService
 *
 * @author xuxx
 * @date 2019-05-13 14:51:42
 * @since  JDK 1.8
 *
 */
public interface BrandService {

	public List<TbBrand> findAll();
	

	/**
	 * 品牌分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult<TbBrand> findPage(int pageNum,int pageSize);
	
	/**
	 * 增加 
	 * @param brand
	 */
	public void add(TbBrand brand);
	
	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public TbBrand findOne(Long id);
	
	/**
	 * 修改
	 * @param brand
	 */
	public void update(TbBrand brand);
	
	
	/**
	 * 删除
	 * @param ids
	 */
	public void delete(Long[] ids);
	
	
	/**
	 * 品牌分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult<TbBrand> findPage(TbBrand brand, int pageNum,int pageSize);


	/** 
	 * @Title: selectOptionList 
	 * @Description: 获取所有品牌，按照 select2 需要的格式，即{id:,text}
	 * @return List<Map<String,String>>  
	 */
	public List<Map<String, String>> selectOptionList();
}
