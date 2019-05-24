package com.xuxx.mall.seckill.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xuxx.entity.PageResult;
import com.xuxx.entity.Result;
import com.xuxx.mall.pojo.TbSeckillGoods;
import com.xuxx.mall.seckill.service.SeckillGoodsService;

/**
 * 
 * @ClassName: SeckillGoodsController
 *
 * @author xuxx
 * @date 2019-05-24 15:50:36
 * @since  JDK 1.8
 *
 */
@RestController
@RequestMapping("/seckillGoods")
public class SeckillGoodsController {

	@Autowired
	private SeckillGoodsService seckillGoodsService;

	/**
	 * 返回全部列表
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSeckillGoods> findAll() {
		return seckillGoodsService.findAll();
	}

	/**
	 * 返回全部列表
	 * 
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult<TbSeckillGoods> findPage(int page, int rows) {
		return seckillGoodsService.findPage(page, rows);
	}

	/**
	 * 增加
	 * 
	 * @param seckillGoods
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbSeckillGoods seckillGoods) {
		try {
			seckillGoodsService.add(seckillGoods);
			return Result.buildSuccessResult("增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.buildFailResult("增加失败");
		}
	}

	/**
	 * 修改
	 * 
	 * @param seckillGoods
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbSeckillGoods seckillGoods) {
		try {
			seckillGoodsService.update(seckillGoods);
			return Result.buildSuccessResult("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.buildFailResult("修改失败");
		}
	}

	/**
	 * 获取实体
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbSeckillGoods findOne(Long id) {
		return seckillGoodsService.findOne(id);
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long[] ids) {
		try {
			seckillGoodsService.delete(ids);
			return Result.buildSuccessResult("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.buildFailResult("删除失败");
		}
	}

	/**
	 * 查询+分页
	 * 
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult<TbSeckillGoods> search(@RequestBody TbSeckillGoods seckillGoods, int page, int rows) {
		return seckillGoodsService.findPage(seckillGoods, page, rows);
	}

	@RequestMapping("/findList")
	public List<TbSeckillGoods> findList() {
		return seckillGoodsService.findList();
	}

	@RequestMapping("/findOneFromRedis")
	public TbSeckillGoods findOneFromRedis(Long id) {
		return seckillGoodsService.findOneFromRedis(id);
	}
}
