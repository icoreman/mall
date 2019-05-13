package com.xuxx.mall.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xuxx.mall.pojo.TbSpecification;
import com.xuxx.mall.pojo.TbSpecificationExample;

public interface TbSpecificationMapper {
	int countByExample(TbSpecificationExample example);

	int deleteByExample(TbSpecificationExample example);

	int deleteByPrimaryKey(Long id);

	int insert(TbSpecification record);

	int insertSelective(TbSpecification record);

	List<TbSpecification> selectByExample(TbSpecificationExample example);

	TbSpecification selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("record") TbSpecification record,
			@Param("example") TbSpecificationExample example);

	int updateByExample(@Param("record") TbSpecification record, @Param("example") TbSpecificationExample example);

	int updateByPrimaryKeySelective(TbSpecification record);

	int updateByPrimaryKey(TbSpecification record);
	
	/**
	 * 
	 * @Title: selectOptionList 
	 * @Description: 获取所有规格选项
	 * @param @return 
	 * @return List<Map<String,String>>    返回类型 
	 * @throws
	 */
	List<Map<String, String>> selectOptionList();
}