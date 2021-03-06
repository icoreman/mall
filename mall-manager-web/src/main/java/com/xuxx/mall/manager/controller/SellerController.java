package com.xuxx.mall.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xuxx.entity.PageResult;
import com.xuxx.entity.Result;
import com.xuxx.mall.pojo.TbSeller;
import com.xuxx.mall.sellergoods.service.SellerService;

/**
 * 
 * @ClassName: SellerController
 *
 * @author xuxx
 * @date 2019-05-14 14:44:48
 * @since JDK 1.8
 *
 */
@RestController
@RequestMapping("/seller")
public class SellerController {

	@Autowired
	private SellerService sellerService;

	/**
	 * 返回全部列表
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSeller> findAll() {
		return sellerService.findAll();
	}

	/**
	 * 返回全部列表
	 * 
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult<TbSeller> findPage(int page, int rows) {
		return sellerService.findPage(page, rows);
	}

	/**
	 * 增加
	 * 
	 * @param seller
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbSeller seller) {
		try {
			sellerService.add(seller);
			return Result.buildSuccessResult("增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.buildFailResult("增加失败");
		}
	}

	/**
	 * 修改
	 * 
	 * @param seller
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbSeller seller) {
		try {
			sellerService.update(seller);
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
	public TbSeller findOne(String id) {
		return sellerService.findOne(id);
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(String[] ids) {
		try {
			sellerService.delete(ids);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
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
	public PageResult<TbSeller> search(@RequestBody TbSeller seller, int page, int rows) {
		return sellerService.findPage(seller, page, rows);
	}

	@RequestMapping("/updateStatus")
	public Result updateStatus(String sellerId, String status) {
		try {
			sellerService.updateStatus(sellerId, status);
			return new Result(true, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "失败");
		}

	}

}
