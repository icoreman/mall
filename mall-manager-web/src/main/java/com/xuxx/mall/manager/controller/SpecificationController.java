package com.xuxx.mall.manager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xuxx.entity.PageResult;
import com.xuxx.entity.Result;
import com.xuxx.mall.pojo.TbSpecification;
import com.xuxx.mall.sellergoods.service.SpecificationService;
import com.xuxx.mall.vo.SpecificationVO;

/**
 * 
 * @ClassName: SpecificationController
 *
 * @author xuxx
 * @date 2019-05-13 17:36:58
 * @since JDK 1.8
 *
 */

@RestController
@RequestMapping("/specification")
public class SpecificationController {

	@Autowired
	private SpecificationService specificationService;

	/**
	 * 返回全部列表
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSpecification> findAll() {
		return specificationService.findAll();
	}

	/**
	 * 返回全部列表
	 * 
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult<TbSpecification> findPage(int page, int rows) {
		return specificationService.findPage(page, rows);
	}

	/**
	 * 增加
	 * 
	 * @param specification
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody SpecificationVO specification) {
		try {
			specificationService.add(specification);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}

	/**
	 * 修改
	 * 
	 * @param specification
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody SpecificationVO specification) {
		try {
			specificationService.update(specification);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}

	/**
	 * 获取实体
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public SpecificationVO findOne(Long id) {
		return specificationService.findOne(id);
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
			specificationService.delete(ids);
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
	public PageResult<TbSpecification> search(@RequestBody TbSpecification specification, int page, int rows) {
		return specificationService.findPage(specification, page, rows);
	}

	@RequestMapping("/selectOptionList")
	public List<Map<String, String>> selectOptionList() {
		return specificationService.selectOptionList();
	}
}
