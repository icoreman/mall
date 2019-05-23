package com.xuxx.mall.order.service;

import java.util.List;

import com.xuxx.entity.PageResult;
import com.xuxx.mall.pojo.TbOrder;

/**
 * 
 * @ClassName: OrderService
 *
 * @author xuxx
 * @date 2019-05-23 15:24:42
 * @since JDK 1.8
 *
 */
public interface OrderService {

	/**
	 * 返回全部列表
	 * 
	 * @return
	 */
	public List<TbOrder> findAll();

	/**
	 * 返回分页列表
	 * 
	 * @return
	 */
	public PageResult<TbOrder> findPage(int pageNum, int pageSize);

	/**
	 * 增加
	 */
	public void add(TbOrder order);

	/**
	 * 修改
	 */
	public void update(TbOrder order);

	/**
	 * 根据ID获取实体
	 * 
	 * @param id
	 * @return
	 */
	public TbOrder findOne(Long id);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 */
	public void delete(Long[] ids);

	/**
	 * 分页
	 * 
	 * @param pageNum  当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult<TbOrder> findPage(TbOrder order, int pageNum, int pageSize);

}
