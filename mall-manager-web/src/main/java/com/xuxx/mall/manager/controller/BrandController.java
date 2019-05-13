package com.xuxx.mall.manager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xuxx.entity.PageResult;
import com.xuxx.entity.Result;
import com.xuxx.mall.pojo.TbBrand;
import com.xuxx.mall.sellergoods.service.BrandService;

/**
 * 
 * @ClassName: BrandController
 *
 * @author xuxx
 * @date 2019-05-13 14:55:26
 * @since JDK 1.8
 *
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

	@Autowired
	private BrandService brandService;

	@RequestMapping("/findAll")
	public List<TbBrand> findAll() {
		return brandService.findAll();
	}

	@RequestMapping("/findPage")
	public PageResult<TbBrand> findPage(int page, int size) {
		return brandService.findPage(page, size);
	}

	@RequestMapping("/add")
	public Result add(@RequestBody TbBrand brand) {
		try {
			brandService.add(brand);
			return Result.buildSuccessResult("增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.buildFailResult("增加失败");
		}
	}

	@RequestMapping("/findOne")
	public TbBrand findOne(Long id) {
		return brandService.findOne(id);
	}

	@RequestMapping("/update")
	public Result update(@RequestBody TbBrand brand) {
		try {
			brandService.update(brand);
			return Result.buildSuccessResult("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.buildFailResult("修改失败");
		}
	}

	@RequestMapping("/delete")
	public Result delete(Long[] ids) {
		try {
			brandService.delete(ids);
			return Result.buildSuccessResult("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.buildFailResult("删除失败");
		}
	}

	@RequestMapping("/search")
	public PageResult<TbBrand> search(@RequestBody TbBrand brand, int page, int rows) {
		return brandService.findPage(brand, page, rows);
	}
	
	/**
	 * 
	 * @Title: selectOptionList 
	 * @Description: 获取所有品牌，按照 select2 需要的格式，即{id:,text}
	 * @return List<Map> 
	 */
	@RequestMapping("/selectOptionList")
	public List<Map<String, String>> selectOptionList() {
		return brandService.selectOptionList();
	}
}
