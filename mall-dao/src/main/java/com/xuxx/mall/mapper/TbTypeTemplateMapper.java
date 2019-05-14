package com.xuxx.mall.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xuxx.mall.pojo.TbTypeTemplate;
import com.xuxx.mall.pojo.TbTypeTemplateExample;

public interface TbTypeTemplateMapper {
    int countByExample(TbTypeTemplateExample example);

    int deleteByExample(TbTypeTemplateExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbTypeTemplate record);

    int insertSelective(TbTypeTemplate record);

    List<TbTypeTemplate> selectByExample(TbTypeTemplateExample example);

    TbTypeTemplate selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbTypeTemplate record, @Param("example") TbTypeTemplateExample example);

    int updateByExample(@Param("record") TbTypeTemplate record, @Param("example") TbTypeTemplateExample example);

    int updateByPrimaryKeySelective(TbTypeTemplate record);

    int updateByPrimaryKey(TbTypeTemplate record);

	/** 
	 * @Title: selectOptionList 
	 * @Description: 获取所有模板选项，按照 select2 需要的格式，即{id:,text} 
	 * @return List<Map<String,String>>
	 */
	List<Map<String, String>> selectOptionList();
}