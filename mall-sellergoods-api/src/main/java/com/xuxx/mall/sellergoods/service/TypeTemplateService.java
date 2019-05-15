package com.xuxx.mall.sellergoods.service;
import java.util.List;
import java.util.Map;

import com.xuxx.entity.PageResult;
import com.xuxx.mall.pojo.TbTypeTemplate;
/**
 * 
 * @ClassName: TypeTemplateService
 *
 * @author xuxx
 * @date 2019-05-13 17:54:48
 * @since  JDK 1.8
 *
 */
public interface TypeTemplateService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbTypeTemplate> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult<TbTypeTemplate> findPage(int pageNum,int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(TbTypeTemplate typeTemplate);
	
	
	/**
	 * 修改
	 */
	public void update(TbTypeTemplate typeTemplate);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbTypeTemplate findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult<TbTypeTemplate> findPage(TbTypeTemplate typeTemplate, int pageNum,int pageSize);
	
	/**
	 * 
	 * @Title: selectOptionList 
	 * @Description: 获取所有模板选项，按照 select2 需要的格式，即{id:,text} 
	 * @return List<Map<String,String>> 
	 */
	public List<Map<String,String>> selectOptionList();


	/** 
	 * @Title: findSpecList 
	 * @Description: 获取模板对应的规格选项
	 * @param id
	 * @return List<Map<String,String>> 
	 */
	public List<Map> findSpecList(Long id);
}
