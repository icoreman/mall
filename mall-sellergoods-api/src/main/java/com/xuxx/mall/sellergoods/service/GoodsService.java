package com.xuxx.mall.sellergoods.service;
import java.util.List;

import com.xuxx.entity.PageResult;
import com.xuxx.mall.pojo.TbGoods;

/**
 * 
 * @ClassName: GoodsService
 *
 * @author xuxx
 * @date 2019-05-13 17:11:51
 * @since  JDK 1.8
 *
 */
public interface GoodsService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbGoods> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult<TbGoods> findPage(int pageNum,int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(TbGoods goods);
	
	
	/**
	 * 修改
	 */
	public void update(TbGoods goods);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbGoods findOne(Long id);
	
	
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
	public PageResult<TbGoods> findPage(TbGoods goods, int pageNum,int pageSize);
	
}
