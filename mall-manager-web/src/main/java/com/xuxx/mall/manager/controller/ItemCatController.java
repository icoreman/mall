package com.xuxx.mall.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xuxx.entity.PageResult;
import com.xuxx.entity.Result;
import com.xuxx.mall.pojo.TbItemCat;
import com.xuxx.mall.sellergoods.service.ItemCatService;

/**
 * 
 * @ClassName: ItemCatController
 *
 * @author xuxx
 * @date 2019-05-14 17:34:15
 * @since JDK 1.8
 *
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;

	/**
	 * 返回全部列表
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbItemCat> findAll() {
		return itemCatService.findAll();
	}

	/**
	 * 返回全部列表
	 * 
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult<TbItemCat> findPage(int page, int rows) {
		return itemCatService.findPage(page, rows);
	}

	/**
	 * 增加
	 * 
	 * @param itemCat
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbItemCat itemCat) {
		try {
			itemCatService.add(itemCat);
			return Result.buildSuccessResult("增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.buildFailResult("增加失败");
		}
	}

	/**
	 * 修改
	 * 
	 * @param itemCat
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbItemCat itemCat) {
		try {
			itemCatService.update(itemCat);
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
	public TbItemCat findOne(Long id) {
		return itemCatService.findOne(id);
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
			itemCatService.delete(ids);
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
	public PageResult<TbItemCat> search(@RequestBody TbItemCat itemCat, int page, int rows) {
		return itemCatService.findPage(itemCat, page, rows);
	}

	/**
	 * 根据上级ID查询商品分类列表
	 * 
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/findByParentId")
	public List<TbItemCat> findByParentId(Long parentId) {
		return itemCatService.findByParentId(parentId);
	}
}