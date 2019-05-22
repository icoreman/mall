package com.xuxx.mall.vo;

import java.io.Serializable;
import java.util.List;

import com.xuxx.mall.pojo.TbOrderItem;

/**
 * 
 * @ClassName: Cart
 *
 * @author xuxx
 * @date 2019-05-22 10:11:33
 * @since JDK 1.8
 *
 */
public class Cart implements Serializable {

	private String sellerId;// 商家ID
	private String sellerName;// 商家名称

	private List<TbOrderItem> orderItemList;// 购物车明细集合

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public List<TbOrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<TbOrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
}
