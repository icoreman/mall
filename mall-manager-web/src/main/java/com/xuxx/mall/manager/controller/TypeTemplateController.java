package com.xuxx.mall.manager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xuxx.entity.PageResult;
import com.xuxx.entity.Result;
import com.xuxx.mall.pojo.TbTypeTemplate;
import com.xuxx.mall.sellergoods.service.TypeTemplateService;

/**
 * 
 * @ClassName: TypeTemplateController
 *
 * @author xuxx
 * @date 2019-05-13 21:08:41
 * @since JDK 1.8
 *
 */
@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {

	@Autowired
	private TypeTemplateService typeTemplateService;

	/**
	 * 返回全部列表
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbTypeTemplate> findAll() {
		return typeTemplateService.findAll();
	}

	/**
	 * 返回全部列表
	 * 
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult<TbTypeTemplate> findPage(int page, int rows) {
		return typeTemplateService.findPage(page, rows);
	}

	/**
	 * 增加
	 * 
	 * @param typeTemplate
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbTypeTemplate typeTemplate) {
		try {
			typeTemplateService.add(typeTemplate);
			return Result.buildSuccessResult("增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.buildFailResult("增加失败");
		}
	}

	/**
	 * 修改
	 * 
	 * @param typeTemplate
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbTypeTemplate typeTemplate) {
		try {
			typeTemplateService.update(typeTemplate);
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
	public TbTypeTemplate findOne(Long id) {
		return typeTemplateService.findOne(id);
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
			typeTemplateService.delete(ids);
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
	public PageResult<TbTypeTemplate> search(@RequestBody TbTypeTemplate typeTemplate, int page, int rows) {
		return typeTemplateService.findPage(typeTemplate, page, rows);
	}
	
	/**
	 * 
	 * @Title: selectOptionList 
	 * @Description: 获取所有类型模板，按照 select2 需要的格式，即{id:,text}
	 * @return List<Map> 
	 */
	@RequestMapping("/selectOptionList")
	public List<Map<String, String>> selectOptionList() {
		return typeTemplateService.selectOptionList();
	}
	
	/** 
	 * @Title: findSpecList 
	 * @Description: 获取模板对应的规格选项
	 * @param id
	 * @return List<Map<String,String>> 
	 */
	@RequestMapping("/findSpecList")
	public List<Map> findSpecList(Long id){
		return typeTemplateService.findSpecList(id);
	}
}
