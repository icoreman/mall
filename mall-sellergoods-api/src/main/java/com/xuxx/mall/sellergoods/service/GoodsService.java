package com.xuxx.mall.sellergoods.service;
import java.util.List;

import com.xuxx.entity.PageResult;
import com.xuxx.mall.pojo.TbGoods;
import com.xuxx.mall.pojo.TbItem;
import com.xuxx.mall.vo.GoodsVO;

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
	public void add(GoodsVO goods);
	
	
	/**
	 * 修改
	 */
	public void update(GoodsVO goods);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public GoodsVO findOne(Long id);
	
	
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


	/** 
	 * @Title: updateStatus 
	 * @Description: 批量修改状态
	 * @param ids
	 * @param status 
	 */
	public void updateStatus(Long[] ids, String status);


	/** 
	 * @Title: updateMarketableStatus 
	 * @Description: 修改上架状态，1为上架，0为下架
	 * @param ids
	 * @param status 
	 */
	public void updateMarketableStatus(Long[] ids, String status);
	/**
	 * 
	 * @Title: findItemListByGoodsIdListAndStatus 
	 * @Description: 通过 spu 的 goodsId 和 status 找到 sku 的商品集合
	 * @param goodsIds
	 * @param status
	 * @return List<TbItem>  
	 */
	public List<TbItem>	findItemListByGoodsIdListAndStatus(Long []goodsIds,String status);
}
