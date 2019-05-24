package com.xuxx.mall.seckill.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xuxx.entity.PageResult;
import com.xuxx.entity.Result;
import com.xuxx.mall.pojo.TbSeckillOrder;
import com.xuxx.mall.seckill.service.SeckillOrderService;

/**
 * 
 * @ClassName: SeckillOrderController
 *
 * @author xuxx
 * @date 2019-05-24 15:50:59
 * @since JDK 1.8
 *
 */
@RestController
@RequestMapping("/seckillOrder")
public class SeckillOrderController {

	@Autowired
	private SeckillOrderService seckillOrderService;

	/**
	 * 返回全部列表
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSeckillOrder> findAll() {
		return seckillOrderService.findAll();
	}

	/**
	 * 返回全部列表
	 * 
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult<TbSeckillOrder> findPage(int page, int rows) {
		return seckillOrderService.findPage(page, rows);
	}

	/**
	 * 增加
	 * 
	 * @param seckillOrder
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbSeckillOrder seckillOrder) {
		try {
			seckillOrderService.add(seckillOrder);
			return Result.buildSuccessResult("增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.buildFailResult("增加失败");
		}
	}

	/**
	 * 修改
	 * 
	 * @param seckillOrder
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbSeckillOrder seckillOrder) {
		try {
			seckillOrderService.update(seckillOrder);
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
	public TbSeckillOrder findOne(Long id) {
		return seckillOrderService.findOne(id);
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
			seckillOrderService.delete(ids);
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
	public PageResult<TbSeckillOrder> search(@RequestBody TbSeckillOrder seckillOrder, int page, int rows) {
		return seckillOrderService.findPage(seckillOrder, page, rows);
	}

	@RequestMapping("/submitOrder")
	public Result submitOrder(Long seckillId) {
		// 提取当前用户
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if ("anonymousUser".equals(username)) {
			return Result.buildFailResult("当前用户未登录");
		}

		try {
			seckillOrderService.submitOrder(seckillId, username);
			return Result.buildSuccessResult("提交订单成功");

		} catch (RuntimeException e) {
			e.printStackTrace();
			return Result.buildFailResult(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.buildFailResult("提交订单失败");
		}
	}
}
