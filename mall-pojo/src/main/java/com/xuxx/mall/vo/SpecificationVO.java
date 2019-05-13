package com.xuxx.mall.vo;

import java.io.Serializable;
import java.util.List;

import com.xuxx.mall.pojo.TbSpecification;
import com.xuxx.mall.pojo.TbSpecificationOption;

/**
 * @ClassName: SpecificationVO
 *
 * @author xuxx
 * @date 2019-05-13 17:23:25
 * @since JDK 1.8
 *
 */
public class SpecificationVO implements Serializable {

	private TbSpecification specification;

	private List<TbSpecificationOption> specificationOptionList;

	public TbSpecification getSpecification() {
		return specification;
	}

	public void setSpecification(TbSpecification specification) {
		this.specification = specification;
	}

	public List<TbSpecificationOption> getSpecificationOptionList() {
		return specificationOptionList;
	}

	public void setSpecificationOptionList(List<TbSpecificationOption> specificationOptionList) {
		this.specificationOptionList = specificationOptionList;
	}
}
