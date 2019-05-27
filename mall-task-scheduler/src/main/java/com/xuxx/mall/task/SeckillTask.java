package com.xuxx.mall.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xuxx.mall.mapper.TbSeckillGoodsMapper;
import com.xuxx.mall.pojo.TbSeckillGoods;
import com.xuxx.mall.pojo.TbSeckillGoodsExample;
import com.xuxx.mall.pojo.TbSeckillGoodsExample.Criteria;

/**
 * 秒杀定时任务
 * 
 * @ClassName: SeckillTask
 *
 * @author xuxx
 * @date 2019-05-25 15:43:43
 * @since  JDK 1.8
 *
 */
@Component
public class SeckillTask {

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private TbSeckillGoodsMapper seckillGoodsMapper;

	/**
	 * 
	 * @Title: refreshSeckillGoods 
	 * @Description: 每分钟定时增量刷新秒杀列表
	 */
	@Scheduled(cron = "0 * * * * ?")
	public void refreshSeckillGoods() {
		System.out.println("执行了秒杀商品增量更新 任务调度" + new Date());

		// 查询缓存中的秒杀商品ID集合
		List goodsIdList = new ArrayList(redisTemplate.boundHashOps("seckillGoods").keys());
		System.out.println(goodsIdList);

		TbSeckillGoodsExample example = new TbSeckillGoodsExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo("1");// 审核通过的商品
		criteria.andStockCountGreaterThan(0);// 库存数大于0
		criteria.andStartTimeLessThanOrEqualTo(new Date());// 开始日期小于等于当前日期
		criteria.andEndTimeGreaterThanOrEqualTo(new Date());// 截止日期大于等于当前日期

		if (goodsIdList.size() > 0) {
			criteria.andIdNotIn(goodsIdList);// 排除缓存中已经存在的商品ID集合
		}

		List<TbSeckillGoods> seckillGoodsList = seckillGoodsMapper.selectByExample(example);
		// 将列表数据装入缓存
		for (TbSeckillGoods seckillGoods : seckillGoodsList) {
			redisTemplate.boundHashOps("seckillGoods").put(seckillGoods.getId(), seckillGoods);
			System.out.println("增量更新秒杀商品ID:" + seckillGoods.getId());
		}
		System.out.println(".....end....");
	}

	/**
	 * 
	 * @Title: removeSeckillGoods 
	 * @Description: 每秒钟检查是否过期
	 */
	@Scheduled(cron = "* * * * * ?")
	public void removeSeckillGoods() {
		// 查询出缓存中的数据，扫描每条记录，判断时间，如果当前时间超过了截止时间，移除此记录
		List<TbSeckillGoods> seckillGoodsList = redisTemplate.boundHashOps("seckillGoods").values();
		System.out.println("执行了清除秒杀商品的任务" + new Date());
		
		for (TbSeckillGoods seckillGoods : seckillGoodsList) {
			if (seckillGoods.getEndTime().getTime() < new Date().getTime()) {
				// 同步到数据库
				seckillGoodsMapper.updateByPrimaryKey(seckillGoods);
				// 清除缓存
				redisTemplate.boundHashOps("seckillGoods").delete(seckillGoods.getId());
				System.out.println("秒杀商品" + seckillGoods.getId() + "已过期");
			}
		}
		System.out.println("执行了清除秒杀商品的任务...end");
	}
}
