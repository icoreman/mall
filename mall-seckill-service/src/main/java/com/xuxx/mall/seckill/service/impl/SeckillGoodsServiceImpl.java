package com.xuxx.mall.seckill.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.dubbo.config.annotation.Service;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuxx.entity.PageResult;
import com.xuxx.mall.mapper.TbSeckillGoodsMapper;
import com.xuxx.mall.pojo.TbSeckillGoods;
import com.xuxx.mall.pojo.TbSeckillGoodsExample;
import com.xuxx.mall.pojo.TbSeckillGoodsExample.Criteria;
import com.xuxx.mall.seckill.service.SeckillGoodsService;

/**
 * 
 * @ClassName: SeckillGoodsServiceImpl
 *
 * @author xuxx
 * @date 2019-05-24 20:10:50
 * @since JDK 1.8
 *
 */

@Service
@Transactional
public class SeckillGoodsServiceImpl implements SeckillGoodsService {
	private static final Logger log = Logger.getLogger(SeckillGoodsServiceImpl.class);
	
	@Autowired
	private TbSeckillGoodsMapper seckillGoodsMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 查询全部
	 */
	@Override
	public List<TbSeckillGoods> findAll() {
		return seckillGoodsMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult<TbSeckillGoods> findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbSeckillGoods> page = (Page<TbSeckillGoods>) seckillGoodsMapper.selectByExample(null);
		return new PageResult<TbSeckillGoods>(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbSeckillGoods seckillGoods) {
		seckillGoodsMapper.insert(seckillGoods);
	}

	/**
	 * 修改
	 */
	@Override
	public void update(TbSeckillGoods seckillGoods) {
		seckillGoodsMapper.updateByPrimaryKey(seckillGoods);
	}

	/**
	 * 根据ID获取实体
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public TbSeckillGoods findOne(Long id) {
		return seckillGoodsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			seckillGoodsMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public PageResult<TbSeckillGoods> findPage(TbSeckillGoods seckillGoods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		TbSeckillGoodsExample example = new TbSeckillGoodsExample();
		Criteria criteria = example.createCriteria();

		if (seckillGoods != null) {
			if (seckillGoods.getTitle() != null && seckillGoods.getTitle().length() > 0) {
				criteria.andTitleLike("%" + seckillGoods.getTitle() + "%");
			}
			if (seckillGoods.getSmallPic() != null && seckillGoods.getSmallPic().length() > 0) {
				criteria.andSmallPicLike("%" + seckillGoods.getSmallPic() + "%");
			}
			if (seckillGoods.getSellerId() != null && seckillGoods.getSellerId().length() > 0) {
				criteria.andSellerIdLike("%" + seckillGoods.getSellerId() + "%");
			}
			if (seckillGoods.getStatus() != null && seckillGoods.getStatus().length() > 0) {
				criteria.andStatusLike("%" + seckillGoods.getStatus() + "%");
			}
			if (seckillGoods.getIntroduction() != null && seckillGoods.getIntroduction().length() > 0) {
				criteria.andIntroductionLike("%" + seckillGoods.getIntroduction() + "%");
			}

		}

		Page<TbSeckillGoods> page = (Page<TbSeckillGoods>) seckillGoodsMapper.selectByExample(example);
		return new PageResult<TbSeckillGoods>(page.getTotal(), page.getResult());
	}

	@Override
	public List<TbSeckillGoods> findList() {
		List<TbSeckillGoods> seckillGoodsList = redisTemplate.boundHashOps("seckillGoods").values();
		if (seckillGoodsList == null || seckillGoodsList.size() == 0) {
			TbSeckillGoodsExample example = new TbSeckillGoodsExample();
			Criteria criteria = example.createCriteria();
			criteria.andStatusEqualTo("1");// 审核通过的商品
			criteria.andStockCountGreaterThan(0);// 库存数大于0
			criteria.andStartTimeLessThanOrEqualTo(new Date());// 开始日期小于等于当前日期
			criteria.andEndTimeGreaterThanOrEqualTo(new Date());// 截止日期大于等于当前日期
			seckillGoodsList = seckillGoodsMapper.selectByExample(example);
			// 将列表数据装入缓存
			for (TbSeckillGoods seckillGoods : seckillGoodsList) {
				redisTemplate.boundHashOps("seckillGoods").put(seckillGoods.getId(), seckillGoods);
			}
			log.info("从数据库中读取数据装入缓存");
		} else {
			log.info("从缓存中读取数据");
		}
		return seckillGoodsList;

	}

	@Override
	public TbSeckillGoods findOneFromRedis(Long id) {
		return (TbSeckillGoods) redisTemplate.boundHashOps("seckillGoods").get(id);
	}
}
